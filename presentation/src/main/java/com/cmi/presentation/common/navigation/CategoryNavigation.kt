package com.cmi.presentation.common.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.cmi.presentation.components.common.add.PictureUploaderContentType
import com.cmi.presentation.components.remover.PictureRemoverForPecs
import com.cmi.presentation.components.remover.type.PictureRemoverContentType
import com.cmi.presentation.components.selecter.PictureSelecterForPecs
import com.cmi.presentation.components.selecter.type.PictureSelecterContentType
import com.cmi.presentation.components.uploader.PictureUploader
import com.cmi.presentation.ktx.defaultLeftToRightComposable
import com.cmi.presentation.ktx.orZero
import com.cmi.presentation.model.CategoryModel
import kotlinx.serialization.Serializable

@Serializable
object CategoryConfigurationScreens

@Serializable
sealed class CategoryConfigurationTypeHost {
    @Serializable
    data object Select : CategoryConfigurationTypeHost()

    @Serializable
    data object Add : CategoryConfigurationTypeHost()

    @Serializable
    data class Edit(val categoryId: Int) : CategoryConfigurationTypeHost()

    @Serializable
    data object Remove : CategoryConfigurationTypeHost()
}

fun NavGraphBuilder.categoryConfigurationNavGraph(navController: NavController) {
    navigation<CategoryConfigurationScreens>(startDestination = CategoryConfigurationTypeHost.Select) {

        defaultLeftToRightComposable<CategoryConfigurationTypeHost.Select> {
            PictureSelecterForPecs(
                pictureSelecterContentType = PictureSelecterContentType.Category
            ) {
                navController.popBackStack()
            }
        }

        defaultLeftToRightComposable<CategoryConfigurationTypeHost.Add> {
            PictureUploader(
                contentType = PictureUploaderContentType.CategoryEntry
            ) {
                navController.popBackStack()
            }
        }

        defaultLeftToRightComposable<CategoryConfigurationTypeHost.Edit> {
            val categoryConfigurationTypeHost = it.toRoute<CategoryConfigurationTypeHost.Edit>()
            val categoryId = categoryConfigurationTypeHost.categoryId
            PictureUploader(
                contentType = PictureUploaderContentType.CategoryEditable(
                    pictureId = categoryId
                )
            ) {
                navController.popBackStack()
            }
        }

        defaultLeftToRightComposable<CategoryConfigurationTypeHost.Remove> {
            PictureRemoverForPecs(
                contentType = PictureRemoverContentType.Category
            ) {
                navController.popBackStack()
            }
        }
    }
}

fun NavController.navigateToCategoryEdit(categoryModel: CategoryModel) {
    navigate(CategoryConfigurationTypeHost.Edit(categoryModel.id.orZero))
}