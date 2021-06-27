package com.cmi.presentation.config.edit

import androidx.recyclerview.widget.DiffUtil
import com.cmi.presentation.model.CategoryModel

class CategoryDiffCallback(private val oldCategories: List<CategoryModel>, private val newCategories: List<CategoryModel>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldCategories.size


    override fun getNewListSize(): Int = newCategories.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldCategories[oldItemPosition].categoryId == newCategories[newItemPosition].categoryId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldCategories[oldItemPosition] == newCategories[newItemPosition]
    }
}