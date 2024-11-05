package com.cmi.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey val categoryId: Int? = null,
    val folder: String?,
    val path: String?,
    val name: String?,
    val priority: Int?,
    val isExternal: Int?,
    val isSelectedForPecs: Int?,
    val isFoundationPath: Int?
)