package com.cmi.presentation.components.common.alert

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.cmi.presentation.R

@Composable
fun DefaultAlertDialog(
    modifier: Modifier = Modifier,
    title: String = stringResource(R.string.text_app_short_name),
    description: String,
    confirmButton: String = stringResource(R.string.text_accept),
    onDismiss: () -> Unit,
) {
    AlertDialog(
        modifier = modifier,
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text(text = confirmButton)
            }
        },
        title = {
            Text(text = title, fontSize = 18.sp)
        },
        text = {
            Text(text = description)
        }
    )
}