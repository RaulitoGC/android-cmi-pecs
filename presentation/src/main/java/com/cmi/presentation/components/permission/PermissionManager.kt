package com.cmi.presentation.components.permission

import android.util.Log
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionManager(
    permissionState: PermissionState,
    deniedContent: @Composable () -> Unit,
) {
    Log.d("TEST", "PermissionManager: ${permissionState.status}")

    if(permissionState.status is PermissionStatus.Denied){
        deniedContent()
    }
}