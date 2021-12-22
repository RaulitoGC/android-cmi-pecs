package com.cmi.data.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cmi.data.DataSourceManager
import com.cmi.data.local.DefaultLocalDataSource
import com.cmi.data.local.database.CmiDataBase
import com.cmi.data.local.preferences.CmiPreferences
import com.cmi.domain.system.System
import timber.log.Timber

object DataServiceLocator {

    private val lock = Any()

    @Volatile
    private var system: System? = null

    @Volatile
    private var cmiDataBase: CmiDataBase? = null

    @Volatile
    private var cmiPreferences: CmiPreferences? = null

    fun provideSystem(context: Context): System = system ?: synchronized(lock) {
        system ?: createSystem(context)
    }

    fun provideSharedPreferences(context: Context, name: String) = context.getSharedPreferences(name, MODE_PRIVATE)

    private fun createSystem(context: Context): System {
        return DataSourceManager(
            localDataSource = DefaultLocalDataSource(
                cmiDataBase = provideDataBase(context),
                cmiPreferences = providePreferences(context)
            )
        ).also {
            system = it
        }
    }

    private fun providePreferences(context: Context): CmiPreferences =
        cmiPreferences ?: synchronized(lock) {
            cmiPreferences ?: createSharedPreferences(context)
        }

    private fun provideDataBase(context: Context): CmiDataBase = cmiDataBase ?: synchronized(lock) {
        cmiDataBase ?: createDataBase(context)
    }

    private fun createSharedPreferences(context: Context): CmiPreferences {
        return CmiPreferences(sharedPreferences = provideSharedPreferences(
            context = context,
            name = "cmi_pecs_preferences")
        ).also {
            cmiPreferences = it
        }
    }

    private fun createDataBase(context: Context): CmiDataBase {
        return Room.databaseBuilder(context, CmiDataBase::class.java, "cmi_pecs_db")
            .createFromAsset("database/pecs_categories.db")
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    Timber.d("onCreateDataBase create DB")
                }

                override fun onOpen(db: SupportSQLiteDatabase) {
                    super.onOpen(db)
                    Timber.d("onCreateDataBase open DB")
                }
            }).build().also {
                cmiDataBase = it
            }
    }

}