package com.cmi.presentation.common.navigation

import androidx.annotation.StringRes
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.cmi.presentation.R
import com.cmi.presentation.components.chooser.category.CategoryChooser
import com.cmi.presentation.components.chooser.pictogram.PictogramChooser
import com.cmi.presentation.ktx.defaultLeftToRightComposable
import com.cmi.presentation.ktx.enterSlidingToLeft
import com.cmi.presentation.ktx.existSlidingToRight
import com.cmi.presentation.ktx.noEnterTransition
import com.cmi.presentation.ktx.noExitTransition
import com.cmi.presentation.ktx.orZero
import kotlinx.serialization.Serializable

@Serializable
object CategoryChooserFlows

@Serializable
sealed class CategoryChooserHost(@StringRes val title: Int){

    @Serializable
    data object EditCategory : CategoryChooserHost(R.string.text_edit_category)

    @Serializable
    data object RemovePictogram: CategoryChooserHost(R.string.text_delete_pictogram)

    @Serializable
    data object SelectPictogramForPecs: CategoryChooserHost(R.string.text_select_pictogram_for_pecs)

    @Serializable
    data object EditPictogram: CategoryChooserHost(R.string.text_edit_pictogram)

    @Serializable
    data object PecsFlow: CategoryChooserHost(R.string.text_toolbar_pecs_flow_category_selection)
}

fun NavGraphBuilder.categoryChooserNavGraph(navController: NavController) {
    navigation<CategoryChooserFlows>(startDestination = CategoryChooserHost.EditCategory) {
        defaultLeftToRightComposable<CategoryChooserHost.EditCategory> {
            CategoryChooser(
                categoryChooserHost = CategoryChooserHost.EditCategory,
                onBack = {
                    navController.popBackStack()
                },
                onItemSelected = { categoryModel ->
                    navController.navigateToCategoryEdit(categoryModel)
                }
            )
        }

        defaultLeftToRightComposable<CategoryChooserHost.EditPictogram> {
            CategoryChooser(
                categoryChooserHost = CategoryChooserHost.EditPictogram,
                onBack = {
                    navController.popBackStack()
                },
                onItemSelected = { categoryModel ->
                    navController.navigateToPictogramChooser(categoryModel)
                }
            )
        }

        defaultLeftToRightComposable<CategoryChooserHost.RemovePictogram> {
            CategoryChooser(
                categoryChooserHost = CategoryChooserHost.RemovePictogram,
                onBack = {
                    navController.popBackStack()
                },
                onItemSelected = { categoryModel ->
                    navController.navigateToRemovePictogram(categoryModel)
                }
            )
        }

        defaultLeftToRightComposable<CategoryChooserHost.SelectPictogramForPecs> {
            CategoryChooser(
                categoryChooserHost = CategoryChooserHost.SelectPictogramForPecs,
                onBack = {
                    navController.popBackStack()
                },
                onItemSelected = { categoryModel ->
                    navController.navigateToSelectPictogramForPecs(categoryModel)
                }
            )
        }

        defaultLeftToRightComposable<CategoryChooserHost.PecsFlow> {
            CategoryChooser(
                categoryChooserHost = CategoryChooserHost.PecsFlow,
                onBack = {
                    navController.popBackStack()
                },
                onItemSelected = { categoryModel ->
                    navController.navigateToPecsPictogramSelection(categoryModel)
                }
            )
        }
    }
}

fun NavController.navigateToCategoryChooserPecsFlow(){
    navigate(CategoryChooserHost.PecsFlow)
}