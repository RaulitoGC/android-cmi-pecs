package com.cmi.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pictogram")
data class PictogramEntity(
    @PrimaryKey val pictogramId: Int? = null,
    val folder: String?,
    val path: String?,
    val name: String?,
    val priority: Int?,
    val isExternal: Int?,
    val isSelectedForPecs: Int?,
    val categoryId: Int?,
    val isFoundationPath: Int?
)