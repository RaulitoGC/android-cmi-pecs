package com.rguzman.cmi.di

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cmi.data.local.database.CmiDataBase
import dagger.Module
import dagger.Provides
import timber.log.Timber

@Module
class AppModule(private val application: Application) {

    @AppScope
    @Provides
    fun provideContext(): Context = application.applicationContext

    @AppScope
    @Provides
    fun provideCmiPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("cmi_pecs_shared_preferences", MODE_PRIVATE)
    }

    @AppScope
    @Provides
    fun provideDataBase(context: Context): CmiDataBase {
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
            }).build()
    }
}