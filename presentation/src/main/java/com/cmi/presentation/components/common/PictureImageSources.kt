package com.cmi.presentation.components.common

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cmi.presentation.R
import com.cmi.presentation.components.permission.PermissionExplainer
import com.cmi.presentation.components.permission.PermissionManager
import com.cmi.presentation.config.add.model.ImageSourceContent
import com.cmi.presentation.config.add.model.ImageSourcePermission
import com.cmi.presentation.config.add.model.PictureLoaderEvent
import com.cmi.presentation.ktx.DefaultHorizontalSpacer
import com.cmi.presentation.ktx.startSettingActivity
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PictureImageSources() {

    val cameraPermissionState =
        rememberPermissionState(permission = ImageSourceContent.CAMERA.permissionName)
    val galleryPermissionState =
        rememberPermissionState(permission = ImageSourceContent.GALLERY.permissionName)

    Row(
        modifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.margin_high)),
        horizontalArrangement = Arrangement.Center
    ) {
        PictureImageSource(
            modifier = Modifier,
            content = ImageSourceContent.CAMERA,
            permissionState = cameraPermissionState,
            permissionStateRequest = {
                cameraPermissionState.launchPermissionRequest()
            }
        )
        DefaultHorizontalSpacer(width = 32.dp)
        PictureImageSource(
            modifier = Modifier,
            content = ImageSourceContent.GALLERY,
            permissionState = galleryPermissionState,
            permissionStateRequest = {
                galleryPermissionState.launchPermissionRequest()
            }
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PictureImageSource(
    modifier: Modifier,
    content: ImageSourceContent,
    permissionStateRequest: () -> Unit,
    permissionState: PermissionState
) {

    val context = LocalContext.current
    var executePermissionManager by remember {
        mutableStateOf(false)
    }

    PictureImageSourceContent(
        modifier = modifier,
        imageSourceContent = content ,
        permissionStateRequest = {
            executePermissionManager = true
            permissionStateRequest()
        }
    )

    if(executePermissionManager) {
        PermissionManager(
            permissionState = permissionState,
            deniedContent = {
                PermissionExplainer(
                    modifier = modifier,
                    handleLaunchSettings = {
                        context.startSettingActivity()
                    },
                    onDismissRequest = {
                        executePermissionManager = false
                    }
                )
            }
        )
    }
}

@Composable
fun PictureImageSourceContent(
    modifier: Modifier,
    imageSourceContent: ImageSourceContent,
    permissionStateRequest: () -> Unit
){
    Column(
        modifier = modifier.clickable {
            permissionStateRequest()
        },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(
                width = dimensionResource(id = R.dimen.upload_image_option_size_width),
                height = dimensionResource(id = R.dimen.upload_image_option_size_height)
            ),
            painter = painterResource(id = imageSourceContent.image),
            contentScale = ContentScale.Crop,
            contentDescription = stringResource(id = imageSourceContent.contentDescription)
        )
        Text(text = stringResource(id = imageSourceContent.title))
    }
}