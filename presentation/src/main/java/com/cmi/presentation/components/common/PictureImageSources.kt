package com.cmi.presentation.components.common

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.core.content.FileProvider
import com.cmi.presentation.R
import com.cmi.presentation.components.permission.PermissionExplainer
import com.cmi.presentation.components.permission.PermissionManager
import com.cmi.presentation.components.permission.rememberPermissionStateSafe
import com.cmi.presentation.config.add.model.ImageSourceContent
import com.cmi.presentation.ktx.DefaultHorizontalSpacer
import com.cmi.presentation.ktx.createImageFile
import com.cmi.presentation.ktx.isTrue
import com.cmi.presentation.ktx.startSettingActivity
import com.cmi.presentation.manager.FileProviderManager
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import timber.log.Timber


@Composable
fun PictureImageSources(
    onPictureTaken: (Uri) -> Unit
) {

    val context = LocalContext.current

    Row(
        modifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.margin_high)),
        horizontalArrangement = Arrangement.Center
    ) {
        CameraPictureImageSource(
            modifier = Modifier,
            context = context,
            onPictureTaken = onPictureTaken
        )
        DefaultHorizontalSpacer(width = 32.dp)
        GalleryPictureImageSource(
            modifier = Modifier,
            context = context,
            onPictureSelected = onPictureTaken
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GalleryPictureImageSource(
    modifier: Modifier,
    context: Context,
    onPictureSelected: (Uri) -> Unit
) {

    val galleryPermissionState =
        rememberPermissionStateSafe(permission = ImageSourceContent.GALLERY.permissionName)

    Timber.d("CameraPictureImageSource: ")
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                onPictureSelected(uri)
            }
        }

    var launchPermissionManager by remember {
        mutableStateOf(false)
    }

    PictureImageSourceContent(
        modifier = modifier,
        imageSourceContent = ImageSourceContent.GALLERY,
        permissionStateRequest = {
            galleryPermissionState.launchPermissionRequest()
            launchPermissionManager = true
        }
    )

    if (launchPermissionManager) {
        PermissionManager(
            permissionState = galleryPermissionState,
            acceptedContent = {
                galleryLauncher.launch("image/*")
                launchPermissionManager = false
            },
            deniedContent = {
                PermissionExplainer(
                    modifier = modifier,
                    handleLaunchSettings = {
                        context.startSettingActivity()
                    },
                    onDismissRequest = {
                        launchPermissionManager = false
                    }
                )
            }
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraPictureImageSource(
    modifier: Modifier,
    context: Context,
    onPictureTaken: (Uri) -> Unit
) {

    val cameraPermissionState =
        rememberPermissionStateSafe(permission = ImageSourceContent.CAMERA.permissionName)

    val file by remember {
        mutableStateOf(context.createImageFile())
    }

    val uri = FileProvider.getUriForFile(
        context,
        FileProviderManager.AUTHORITY,
        file
    )

    Timber.d("CameraPictureImageSource: ")
    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { result ->
            if (result.isTrue) {
                onPictureTaken(uri)
            }
        }

    var launchPermissionManager by remember {
        mutableStateOf(false)
    }

    PictureImageSourceContent(
        modifier = modifier,
        imageSourceContent = ImageSourceContent.CAMERA,
        permissionStateRequest = {
            cameraPermissionState.launchPermissionRequest()
            launchPermissionManager = true
        }
    )

    if (launchPermissionManager) {
        PermissionManager(
            permissionState = cameraPermissionState,
            acceptedContent = {
                cameraLauncher.launch(uri)
                launchPermissionManager = false
            },
            deniedContent = {
                PermissionExplainer(
                    modifier = modifier,
                    handleLaunchSettings = {
                        context.startSettingActivity()
                    },
                    onDismissRequest = {
                        launchPermissionManager = false
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
) {

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