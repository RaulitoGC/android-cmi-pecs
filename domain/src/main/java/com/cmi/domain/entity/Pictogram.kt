package com.cmi.domain.entity

data class Pictogram(
    val pictogramId: Int? = null,
    val folder: String?,
    val path: String?,
    val name: String?,
    val priority: Int?,
    val isExternal: Boolean?,
    val isSelected: Boolean?,
    val categoryId: Int?,
)