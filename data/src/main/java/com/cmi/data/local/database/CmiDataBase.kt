package com.cmi.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cmi.data.local.database.dao.CategoryDao
import com.cmi.data.local.database.dao.PictogramDao
import com.cmi.data.local.database.entity.CategoryEntity
import com.cmi.data.local.database.entity.PictogramEntity


@Database(
    entities = [(CategoryEntity::class), (PictogramEntity::class)],
    version = 1,
    exportSchema = false
)
abstract class CmiDataBase : RoomDatabase() {

    abstract val pictogramDao: PictogramDao
    abstract val categoryDao: CategoryDao
}