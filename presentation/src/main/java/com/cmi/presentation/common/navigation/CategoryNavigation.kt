package com.cmi.presentation.common.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.cmi.presentation.components.common.add.PictureUploaderContentType
import com.cmi.presentation.config.ConfigurationRootScreen
import com.cmi.presentation.components.uploader.PictureUploader
import com.cmi.presentation.config.category.select.SelectCategoryForPecs
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

        composable<CategoryConfigurationTypeHost.Select> {
            SelectCategoryForPecs(navController = navController)
        }

        composable<CategoryConfigurationTypeHost.Add> {
            PictureUploader(
                navController = navController,
                contentType = PictureUploaderContentType.CategoryEntry
            )
        }

        composable<CategoryConfigurationTypeHost.Edit> {
            val categoryConfigurationTypeHost = it.toRoute<CategoryConfigurationTypeHost.Edit>()
            val categoryId = categoryConfigurationTypeHost.categoryId
            PictureUploader(
                navController = navController,
                contentType = PictureUploaderContentType.CategoryEditable(
                    pictureId = categoryId
                )
            )
        }

        composable<CategoryConfigurationTypeHost.Remove> {
            SelectCategoryForPecs(navController = navController)
        }
    }
}

fun NavController.navigateToCategoryEdit(categoryModel: CategoryModel) {
    navigate(CategoryConfigurationTypeHost.Edit(categoryModel.id.orZero))
}