package com.cmi.domain.entity

data class Category(
    val categoryId: Int? = null,
    val folder: String?,
    val path: String?,
    val name: String?,
    val priority: Int?,
    val isExternal: Boolean?,
    val isSelected: Boolean?
)