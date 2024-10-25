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

    fun copyName(name: String?): PictureModel
    fun copyPath(path: String?): PictureModel
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
    val isSelectedUiEnabled: Boolean = false
): PictureModel {

    override fun copyName(name: String?): PictureModel {
        return this.copy(name = name)
    }

    override fun copyPath(path: String?): PictureModel {
        return this.copy(path = path)
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
    val categoryId: Int? = null,
    val categoryName: String? = null
): PictureModel {
    override fun copyName(name: String?): PictureModel {
        return this.copy(name = name)
    }
    override fun copyPath(path: String?): PictureModel {
        return this.copy(path = path)
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