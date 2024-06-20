package com.cmi.presentation.config.add.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.cmi.presentation.model.CategorySelectableModel
import kotlinx.coroutines.launch

@Composable
fun CarouselWithButtons(items: List<CategorySelectableModel>) {
    // Create a CoroutineScope tied to the current composable's lifecycle
    val coroutineScope = rememberCoroutineScope()
    // Create a LazyListState to control the scroll position of the LazyRow
    val state = rememberLazyListState()
    // Create a mutable state to keep track of the current item in the carousel
    val currentItem = remember { mutableIntStateOf(0) }

    // Create a Row with space between its children
    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        // Create a Button for navigating to the previous item
        Button(onClick = {
            // If the current item is not the first one
            if (currentItem.value > 0) {
                // Decrement the current item
                currentItem.value--
                // Scroll the LazyRow to the current item
                coroutineScope.launch {
                    state.animateScrollToItem(currentItem.value)
                }
            }
        }) {
            // Set the button's label
            Text("Previous")
        }

        // Create a LazyRow for the carousel
        LazyRow(state = state) {
            // Create an item in the LazyRow for each item in the list
            items(items.size) { index ->
                // Replace this with your actual item composable
                Text(items[index].categoryModel.name.orEmpty())
            }
        }

        // Create a Button for navigating to the next item
        Button(onClick = {
            // If the current item is not the last one
            if (currentItem.value < items.size - 1) {
                // Increment the current item
                currentItem.value++
                // Scroll the LazyRow to the current item
                coroutineScope.launch {
                    state.animateScrollToItem(currentItem.value)
                }
            }
        }) {
            // Set the button's label
            Text("Next")
        }
    }
}