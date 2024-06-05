package com.cmi.presentation.components.common

import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.cmi.presentation.R

@Composable
fun PictureNameTextField(
    pictureName: String,
    onPictureNameChange: (String) -> Unit
) {
    OutlinedTextField(
        value = pictureName,
        onValueChange = onPictureNameChange,
        singleLine = true,
        label = {
            Text(
                text = stringResource(id = R.string.text_hint_name)
            )
        }
    )
}