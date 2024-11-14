package com.cmi.presentation.model

import kotlinx.serialization.Serializable

@Serializable
sealed interface PictureModel{
    val id: Int?
    val folder: String?
    val path: String?
    val name: String?
    val priority: Int?
    val isExternal: Boolean?
    val isFoundationPath: Boolean?
    val isSelectedForPecs: Boolean?

    /**
     * [isSelectedForUiEnabled] is used to enable or disable Radio button in UI
     */
    val isSelectedForUiEnabled: Boolean?

    /**
     * [isSelected] is used to know if this items was selected for specific action
     */
    val isSelected: Boolean?

    fun copyName(name: String?): PictureModel
    fun copyPath(path: String?): PictureModel
    fun copyIsFoundationPath(isFoundationPath: Boolean): PictureModel
    fun copySelectedForPecs(isSelected: Boolean): PictureModel
    fun copyIsSelected(isSelected: Boolean): PictureModel
    fun reset(): PictureModel
}


@Serializable
data class CategoryModel(
    override val id: Int? = null,
    override val folder: String? = null,
    override val path: String? = null,
    override val name: String? = null,
    override val priority: Int? = 0,
    override val isExternal: Boolean? = null,
    override val isSelectedForPecs: Boolean? = null,
    override val isSelected: Boolean? = null,
    override val isSelectedForUiEnabled: Boolean? = null,
    override val isFoundationPath: Boolean? = null
): PictureModel {

    override fun copyName(name: String?): PictureModel {
        return this.copy(name = name)
    }

    override fun copyPath(path: String?): PictureModel {
        return this.copy(path = path)
    }

    override fun copyIsFoundationPath(isFoundationPath: Boolean): PictureModel {
        return this.copy(isFoundationPath = isFoundationPath)
    }

    override fun copySelectedForPecs(isSelected: Boolean): PictureModel {
        return this.copy(isSelectedForPecs = isSelected)
    }

    override fun copyIsSelected(isSelected: Boolean): PictureModel {
        return this.copy(isSelected = isSelected)
    }

    override fun reset(): PictureModel {
        return CategoryModel()
    }
}

@Serializable
data class PictogramModel(
    override val id: Int? = null,
    override val folder: String? = null,
    override val path: String? = null,
    override val name: String? = null,
    override val priority: Int? = 0,
    override val isExternal: Boolean? = null,
    override val isSelectedForPecs: Boolean? = null,
    override val isSelected: Boolean? = null,
    override val isSelectedForUiEnabled: Boolean? = null,
    override val isFoundationPath: Boolean? = null,
    val categoryId: Int? = null,
    val categoryName: String? = null
): PictureModel, java.io.Serializable {
    override fun copyName(name: String?): PictureModel {
        return this.copy(name = name)
    }
    override fun copyPath(path: String?): PictureModel {
        return this.copy(path = path)
    }

    override fun copySelectedForPecs(isSelected: Boolean): PictureModel {
        return this.copy(isSelectedForPecs = isSelected)
    }

    override fun copyIsSelected(isSelected: Boolean): PictureModel {
        return this.copy(isSelected = isSelected)
    }

    override fun copyIsFoundationPath(isFoundationPath: Boolean): PictureModel {
        return this.copy(isFoundationPath = isFoundationPath)
    }

    override fun reset(): PictureModel {
        return PictogramModel()
    }
}

fun PictureModel.isCategory(): Boolean {
    return this is CategoryModel
}

private const val ATTRIBUTE_CATEGORY_ID = 2
private const val ACTION_CATEGORY_ID = 18

fun PictogramModel.isAction(): Boolean {
    return categoryId == ACTION_CATEGORY_ID
}

fun PictogramModel.isAttribute(): Boolean {
    return categoryId == ATTRIBUTE_CATEGORY_ID
}

fun CategoryModel.isAttribute(): Boolean {
    return id == ATTRIBUTE_CATEGORY_ID
}

fun CategoryModel.isAction(): Boolean {
    return id == ACTION_CATEGORY_ID
}

fun PictureModel.isPictogram(): Boolean {
    return this is PictogramModel
}

// RGC Validate to remove below code
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