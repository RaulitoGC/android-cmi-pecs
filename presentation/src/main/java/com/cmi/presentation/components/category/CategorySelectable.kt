package com.cmi.presentation.components.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategorySelectable(
    categorySelectableViewModel: CategorySelectableViewModel = koinViewModel(),
    onItemSelected: (Int) -> Unit
) {

    val categoryState = categorySelectableViewModel.uiState.collectAsState().value

    if(categoryState.isLoading) {
        // Show shimmer effect
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(16.dp),
            content = {
                items(6) { // Replace 6 with the number of shimmer items you want to display
                    ShimmerEffect()
                }
            }
        )
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(16.dp),
            content = {
                items(categoryState.categories.size) { item ->
                    val category = categoryState.categories[item]
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable { onItemSelected(item) }
                            .background(Color.LightGray)
                            .fillMaxWidth()
                            .height(100.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = category.name.orEmpty())
                    }
                }
            }
        )
    }
}

@Composable
fun ShimmerEffect() {
    // Define your shimmer effect here
    Box(
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxWidth()
            .height(100.dp)
    )
}