package com.cmi.presentation

import android.content.Intent
import android.content.IntentSender
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.cmi.presentation.ktx.showMessage
import com.cmi.presentation.manager.TextToSpeechManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.ActivityResult
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import timber.log.Timber

class CmiActivity : AppCompatActivity() {

    companion object {
        const val UPDATE_APP_REQUEST_CODE = 145

        const val DAYS_FOR_FLEXIBLE_UPDATE = 7
    }

    private lateinit var textToSpeechManager: TextToSpeechManager
    private lateinit var appUpdateManager: AppUpdateManager

    private val listener = InstallStateUpdatedListener{ state ->
        if (state.installStatus() == InstallStatus.DOWNLOADED) {
            // After the update is downloaded, show a notification
            // and request user confirmation to restart the app.
            popupSnackbarForCompleteUpdate()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cmi)
        textToSpeechManager = TextToSpeechManager(this)

        verifyNewVersion()
    }

    override fun onStart() {
        super.onStart()
        registerListener()
    }

    override fun onResume() {
        super.onResume()
        appUpdateManager
            .appUpdateInfo // Returns an intent object that you use to check for an update.
            .addOnSuccessListener { appUpdateInfo -> // Adds a listener that is called if the Task completes successfully
                Timber.d("Success Listener")
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    // If an in-app update is already running, resume the update.
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        AppUpdateType.IMMEDIATE,
                        this@CmiActivity,
                        UPDATE_APP_REQUEST_CODE
                    )
                }

                if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                    popupSnackbarForCompleteUpdate()
                }

            }.addOnFailureListener { exception ->
                Timber.e(exception)
            }
    }

    private fun verifyNewVersion() {
        Timber.d("Verifying new Version")
        initAppUpdateManager()
        appUpdateManager
            .appUpdateInfo // Returns an intent object that you use to check for an update.
            .addOnSuccessListener { appUpdateInfo -> // Adds a listener that is called if the Task completes successfully
                Timber.d("Success Listener")
                Timber.d("Days since the last update notified + ${appUpdateInfo.clientVersionStalenessDays()}")
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE //Returns whether an update is available for the app.
                    //&& (appUpdateInfo.clientVersionStalenessDays() ?: -1) >= DAYS_FOR_FLEXIBLE_UPDATE // check the number of days since the update became available on the Play Store
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
                ) {
                    startUpdate(appUpdateInfo)
                }
            }.addOnFailureListener { exception -> // Adds a listener that is called if the Task fails.
                Timber.e(exception)
            }
    }

    private fun startUpdate(appUpdateInfo: AppUpdateInfo) {
        try {
            initAppUpdateManager()
            appUpdateManager.startUpdateFlowForResult(
                appUpdateInfo,
                AppUpdateType.IMMEDIATE,
                this@CmiActivity,
                UPDATE_APP_REQUEST_CODE
            )
        } catch (e: IntentSender.SendIntentException) {
            Timber.e(e)
            e.printStackTrace()
        }
    }

    fun provideTextToSpeechManager(): TextToSpeechManager {
        return if (::textToSpeechManager.isInitialized) {
            textToSpeechManager
        } else {
            TextToSpeechManager(this)
        }
    }

    private fun initAppUpdateManager() {
        val isAppUpdateManagerInitialized = ::appUpdateManager.isInitialized
        if (!isAppUpdateManagerInitialized) {
            appUpdateManager = AppUpdateManagerFactory.create(this)
        }
    }

    // Displays the snackbar notification and call to action.
    private fun popupSnackbarForCompleteUpdate() {
        Snackbar.make(
            findViewById(R.id.navHostFragment),
            "An update has just been downloaded.",
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction("RESTART") { appUpdateManager.completeUpdate() }
            setActionTextColor(Color.BLUE)
            show()
        }
    }

    private fun registerListener(){
        appUpdateManager.registerListener(listener)
    }

    private fun unregisterListener(){
        appUpdateManager.unregisterListener(listener)
    }

    override fun onStop() {
        super.onStop()
        unregisterListener()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UPDATE_APP_REQUEST_CODE) {
            when(resultCode){
                RESULT_OK -> {
                    // For immediate updates, you might not receive this callback
                    showMessage("The user has accepted the update")
                }

                RESULT_CANCELED -> {
                    showMessage("The user has denied or canceled the update")
                }

                ActivityResult.RESULT_IN_APP_UPDATE_FAILED -> {
                    showMessage("Some other error prevented either the user from providing consent or the update from proceeding")
                }

                else -> {
                    showMessage("Something went wrong")
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        textToSpeechManager.shutdown()
    }
}