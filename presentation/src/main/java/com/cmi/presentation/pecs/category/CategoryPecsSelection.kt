package com.cmi.presentation.pecs.category

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.cmi.presentation.model.CategoryModel

@Composable
fun CategoryPecsSelection(
    onCategorySelected: (categoryModel: CategoryModel) -> Unit
){
    Text("Category Selection for Pecs")
}