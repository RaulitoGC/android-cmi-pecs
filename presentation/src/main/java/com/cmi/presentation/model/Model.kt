package com.cmi.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryModel(
    val categoryId: Int? = null,
    val folder: String?,
    val path: String?,
    val name: String?,
    val priority: Int?,
    val isExternal: Boolean?,
    val isSelected: Boolean?
) : Parcelable

@Parcelize
data class PictogramModel(
    val pictogramId: Int? = null,
    val folder: String?,
    val path: String?,
    val name: String?,
    val priority: Int?,
    val isExternal: Boolean?,
    val categoryId: Int?,
    val isSelected: Boolean?
): Parcelable

interface SelectableItem {
    var isSelected: Boolean
}

data class PictogramSelectableModel(
    override var isSelected: Boolean = false,
    val pictogramModel: PictogramModel
) : SelectableItem


data class CategorySelectableModel(
    override var isSelected: Boolean = false,
    val categoryModel: CategoryModel
) : SelectableItem