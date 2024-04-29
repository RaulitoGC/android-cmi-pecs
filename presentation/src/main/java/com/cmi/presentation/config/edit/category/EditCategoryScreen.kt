package com.cmi.presentation.config.edit.category

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.graphics.Color


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCategoryScreen() {
    val navController = rememberNavController()
    TopAppBar(
        title = { Text(text = "Your Title") },
        navigationIcon = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    tint = Color.White,
                    contentDescription = "Back"
                )
            }
        }
    )
}

@Preview(
    showBackground = true,
    heightDp = 360,
    widthDp = 800
)
@Composable
fun EditCategoryScreenPreview(){
    EditCategoryScreen()
}