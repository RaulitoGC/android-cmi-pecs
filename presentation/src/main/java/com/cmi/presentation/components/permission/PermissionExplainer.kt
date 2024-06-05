package com.cmi.presentation.components.permission

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.cmi.presentation.R

@Composable
fun PermissionExplainer(
    modifier: Modifier = Modifier,
    handleLaunchSettings: () -> Unit,
    onDismissRequest: () -> Unit
) {
//    var showPermissionExplainer by remember {
//        mutableStateOf(true)
//    }
//    if(showPermissionExplainer) {
        AlertDialog(
            modifier = modifier,
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
            onDismissRequest = {
//                showPermissionExplainer = false
                onDismissRequest()
            },
            confirmButton = {
                TextButton(onClick = { handleLaunchSettings() }) {
                    Text(text = stringResource(id = R.string.text_navigate_to_settings))
                }
            },
            title = {
                Text(text = stringResource(id = R.string.text_title_permission_permanently_denied), fontSize = 18.sp)
            },
            text = {
                Text(stringResource(id = R.string.text_message_permission_permanently_denied))
            }
        )
//    }
}

@Preview(showBackground = true)
@Composable
fun Preview_PermissionExplainer() {
    PermissionExplainer(
        modifier = Modifier.fillMaxWidth(),
        handleLaunchSettings = {},
        onDismissRequest = {}
    )
}