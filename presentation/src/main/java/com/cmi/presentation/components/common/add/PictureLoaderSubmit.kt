package com.cmi.presentation.components.common.add

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cmi.presentation.R
import com.cmi.presentation.ui.theme.CmiThemeExtensions


@Composable
fun PictureLoaderUploadButton(
    modifier: Modifier = Modifier,
    @StringRes text: Int,
    onClick: () -> Unit
) {

    Button(
        modifier = modifier
            .padding(vertical = 12.dp)
            .width(dimensionResource(id = R.dimen.button_width_size)),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = CmiThemeExtensions.colors.primaryButtonSurface),
        onClick = onClick
    ) {
        Text(
            text = stringResource(id = text),
            color = CmiThemeExtensions.colors.textButton
        )
    }
}