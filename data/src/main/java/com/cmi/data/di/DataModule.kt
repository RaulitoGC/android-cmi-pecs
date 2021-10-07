package com.cmi.data.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cmi.data.DataSourceManager
import com.cmi.data.local.LocalDataSource
import com.cmi.data.local.database.CmiDataBase
import com.cmi.data.local.preferences.CmiPreferences
import com.cmi.domain.system.CmiSystem
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideCmiPreferences(sharedPreferences: SharedPreferences): CmiPreferences {
        return CmiPreferences(sharedPreferences)
    }

    @Provides
    fun provideSystem(cmiDataBase: CmiDataBase, cmiPreferences: CmiPreferences): CmiSystem {
        return DataSourceManager(
            localDataSource = LocalDataSource(
                cmiDataBase = cmiDataBase,
                cmiPreferences = cmiPreferences
            )
        )
    }
}