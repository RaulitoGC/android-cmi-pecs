package com.cmi.presentation.common.navigation

import androidx.compose.material.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.cmi.presentation.components.chooser.pictogram.PictogramChooser
import com.cmi.presentation.components.common.add.PictureUploaderContentType
import com.cmi.presentation.components.remover.PictureRemoverForPecs
import com.cmi.presentation.components.remover.type.PictureRemoverContentType
import com.cmi.presentation.components.selecter.PictureSelecterForPecs
import com.cmi.presentation.components.selecter.type.PictureSelecterContentType
import com.cmi.presentation.components.uploader.PictureUploader
import com.cmi.presentation.ktx.orZero
import com.cmi.presentation.model.CategoryModel
import com.cmi.presentation.model.PictogramModel
import kotlinx.serialization.Serializable

@Serializable
object PictogramConfigurationScreens

@Serializable
sealed class PictogramConfigurationTypeHost {

    @Serializable
    data class Select(val categoryId: Int) : PictogramConfigurationTypeHost()

    @Serializable
    data object Add : PictogramConfigurationTypeHost()

    @Serializable
    data class Edit(val pictogramId: Int) : PictogramConfigurationTypeHost()

    @Serializable
    data class Remove(val categoryId: Int) : PictogramConfigurationTypeHost()

    @Serializable
    data class Chooser(val categoryId: Int) : PictogramConfigurationTypeHost()
}

fun NavGraphBuilder.pictogramConfigurationNavGraph(navController: NavController) {
    navigation<PictogramConfigurationScreens>(startDestination = PictogramConfigurationTypeHost.Select(0)) {

        composable<PictogramConfigurationTypeHost.Select> {
            val pictogramConfigurationTypeHost = it.toRoute<PictogramConfigurationTypeHost.Select>()
            val categoryId = pictogramConfigurationTypeHost.categoryId
            PictureSelecterForPecs(
                pictureSelecterContentType = PictureSelecterContentType.Pictogram(categoryId)
            ) {
                navController.popBackStack()
            }
        }

        composable<PictogramConfigurationTypeHost.Add> {
            PictureUploader(
                contentType = PictureUploaderContentType.PictogramEntry
            ){
                navController.popBackStack()
            }
        }

        composable<PictogramConfigurationTypeHost.Edit> {
            val pictogramConfigurationTypeHost = it.toRoute<PictogramConfigurationTypeHost.Edit>()
            val pictogramId = pictogramConfigurationTypeHost.pictogramId
            PictureUploader(
                contentType = PictureUploaderContentType.PictogramEditable(
                    pictureId = pictogramId
                )
            ){
                navController.popBackStack()
            }

        }

        composable<PictogramConfigurationTypeHost.Remove> {
            val pictogramConfigurationTypeHost = it.toRoute<PictogramConfigurationTypeHost.Select>()
            val categoryId = pictogramConfigurationTypeHost.categoryId
            PictureRemoverForPecs(
                contentType = PictureRemoverContentType.Pictogram(categoryId)
            ) {
                navController.popBackStack()
            }
        }

        composable<PictogramConfigurationTypeHost.Chooser> {
            val pictogramConfigurationTypeHost = it.toRoute<PictogramConfigurationTypeHost.Chooser>()
            val categoryId = pictogramConfigurationTypeHost.categoryId

            PictogramChooser(
                categoryId = categoryId,
                onBack = {
                    navController.popBackStack()
                },
                onItemSelected = { pictogramModel ->
                    navController.navigate(PictogramConfigurationTypeHost.Edit(pictogramModel.id.orZero))
                }
            )
        }
    }
}

fun NavController.navigateToSelectPictogramForPecs(categoryModel: CategoryModel) {
    navigate(PictogramConfigurationTypeHost.Select(categoryModel.id.orZero))
}

fun NavController.navigateToPictogramChooser(categoryModel: CategoryModel) {
    navigate(PictogramConfigurationTypeHost.Chooser(categoryModel.id.orZero))
}