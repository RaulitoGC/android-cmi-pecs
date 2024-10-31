package com.cmi.presentation.common.navigation

import androidx.compose.material.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.cmi.presentation.components.common.add.PictureUploaderContentType
import com.cmi.presentation.components.uploader.PictureUploader
import com.cmi.presentation.config.ConfigurationRootScreen
import kotlinx.serialization.Serializable

@Serializable
object PictogramConfigurationScreens

@Serializable
sealed class PictogramConfigurationTypeHost {
    @Serializable
    data object Select : PictogramConfigurationTypeHost()

    @Serializable
    data object Add : PictogramConfigurationTypeHost()

    @Serializable
    data object Edit : PictogramConfigurationTypeHost()

    @Serializable
    data object Remove : PictogramConfigurationTypeHost()
}

fun NavGraphBuilder.pictogramConfigurationNavGraph(navController: NavController) {
    navigation<PictogramConfigurationScreens>(startDestination = PictogramConfigurationTypeHost.Select) {

        composable<PictogramConfigurationTypeHost.Select> {
            Text("not implemented yet")
        }

        composable<PictogramConfigurationTypeHost.Add> {
            PictureUploader(
                contentType = PictureUploaderContentType.PictogramEntry
            ){
                navController.popBackStack()
            }
        }

        composable<PictogramConfigurationTypeHost.Edit> {
            Text("not implemented yet")
        }

        composable<PictogramConfigurationTypeHost.Remove> {
            Text("not implemented yet")
        }
    }
}
