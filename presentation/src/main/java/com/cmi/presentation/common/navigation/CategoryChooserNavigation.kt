package com.cmi.presentation.common.navigation

import androidx.annotation.StringRes
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.cmi.presentation.R
import com.cmi.presentation.config.ConfigurationRootScreen
import com.cmi.presentation.config.ConfigurationScreen
import com.cmi.presentation.config.FLOW
import com.cmi.presentation.config.category.common.CategoryChooser
import kotlinx.serialization.Serializable

@Serializable
object CategoryChooserFlows

@Serializable
sealed class CategoryChooserHost(@StringRes val title: Int){

    @Serializable
    data object EditCategory : CategoryChooserHost(R.string.text_edit_category)

    @Serializable
    data object DeletePictogram: CategoryChooserHost(R.string.text_delete_pictogram)

    @Serializable
    data object SelectPictogramForPecs: CategoryChooserHost(R.string.text_select_pictogram_for_pecs)

    @Serializable
    data object EditPictogram: CategoryChooserHost(R.string.text_edit_pictogram)
}

fun NavGraphBuilder.categoryChooserNavGraph(navController: NavController) {
    navigation<CategoryChooserFlows>(startDestination = CategoryChooserHost.EditCategory) {
        composable<CategoryChooserHost.EditCategory> { _ ->
            CategoryChooser(
                categoryChooserHost = CategoryChooserHost.EditCategory,
                navController = navController
            )
        }

        composable<CategoryChooserHost.EditPictogram> { _ ->
            CategoryChooser(
                categoryChooserHost = CategoryChooserHost.EditPictogram,
                navController = navController
            )
        }

        composable<CategoryChooserHost.DeletePictogram> { _ ->
            CategoryChooser(
                categoryChooserHost = CategoryChooserHost.DeletePictogram,
                navController = navController
            )
        }

        composable<CategoryChooserHost.SelectPictogramForPecs> { _ ->
            CategoryChooser(
                categoryChooserHost = CategoryChooserHost.SelectPictogramForPecs,
                navController = navController
            )
        }
    }
}
