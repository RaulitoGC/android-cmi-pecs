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
    val isSelectedForPecs: Boolean?

    /**
     * [isSelectedForUiEnabled] is used to enable or disable Radio button in UI
     */
    val isSelectedForUiEnabled: Boolean?

    /**
     * [isSelectedForRemoval] is used to know if this items was selected for deletion
     */
    val isSelectedForRemoval: Boolean?

    fun copyName(name: String?): PictureModel
    fun copyPath(path: String?): PictureModel
    fun copyIsExternal(isExternal: Boolean): PictureModel
    fun copySelectedForPecs(isSelected: Boolean): PictureModel
    fun copySelectedForRemoval(isSelected: Boolean): PictureModel
    fun reset(): PictureModel
}


@Serializable
data class CategoryModel(
    override val id: Int? = null,
    override val folder: String? = null,
    override val path: String? = null,
    override val name: String? = null,
    override val priority: Int? = 0,
    override val isExternal: Boolean? = false,
    override val isSelectedForPecs: Boolean? = false,
    override val isSelectedForRemoval: Boolean? = false,
    override val isSelectedForUiEnabled: Boolean = false
): PictureModel {

    override fun copyName(name: String?): PictureModel {
        return this.copy(name = name)
    }

    override fun copyPath(path: String?): PictureModel {
        return this.copy(path = path)
    }

    override fun copyIsExternal(isExternal: Boolean): PictureModel {
        return this.copy(isExternal = isExternal)
    }

    override fun copySelectedForPecs(isSelected: Boolean): PictureModel {
        return this.copy(isSelectedForPecs = isSelected)
    }

    override fun copySelectedForRemoval(isSelected: Boolean): PictureModel {
        return this.copy(isSelectedForRemoval = isSelected)
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
    override val isExternal: Boolean? = false,
    override val isSelectedForPecs: Boolean? = false,
    override val isSelectedForRemoval: Boolean? = false,
    override val isSelectedForUiEnabled: Boolean = false,
    val categoryId: Int? = null,
    val categoryName: String? = null
): PictureModel {
    override fun copyName(name: String?): PictureModel {
        return this.copy(name = name)
    }
    override fun copyPath(path: String?): PictureModel {
        return this.copy(path = path)
    }

    override fun copyIsExternal(isExternal: Boolean): PictureModel {
        return this.copy(isExternal = isExternal)
    }

    override fun copySelectedForPecs(isSelected: Boolean): PictureModel {
        return this.copy(isSelectedForPecs = isSelected)
    }

    override fun copySelectedForRemoval(isSelected: Boolean): PictureModel {
        return this.copy(isSelectedForRemoval = isSelected)
    }

    override fun reset(): PictureModel {
        return PictogramModel()
    }
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