package com.cmi.presentation.components.common

import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.cmi.presentation.R
import com.cmi.presentation.ui.theme.CmiThemeExtensions

@Composable
fun PictureNameTextField(
    modifier: Modifier = Modifier,
    pictureName: String,
    onPictureNameChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = pictureName,
        textStyle = CmiThemeExtensions.typography.h1.copy(
            color = CmiThemeExtensions.colors.primaryTextDisabled,
            fontSize = dimensionResource(R.dimen.text_input_edit_text_size).value.sp
        ),
        onValueChange = onPictureNameChange,
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = CmiThemeExtensions.colors.primaryTextDisabled,
            unfocusedBorderColor = CmiThemeExtensions.colors.primaryTextDisabled,
            cursorColor = CmiThemeExtensions.colors.primaryTextDisabled
        ),
        label = {
            Text(
                text = stringResource(id = R.string.text_hint_name),
                color = CmiThemeExtensions.colors.primaryTextDisabled,
                fontSize = dimensionResource(R.dimen.text_input_edit_text_size).value.sp
            )
        }
    )
}