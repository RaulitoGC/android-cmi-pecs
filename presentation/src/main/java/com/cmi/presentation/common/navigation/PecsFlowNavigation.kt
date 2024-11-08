package com.cmi.presentation.common.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.cmi.presentation.ktx.orZero
import com.cmi.presentation.model.CategoryModel
import com.cmi.presentation.pecs.pictogram.PecsFlowPictogramSelection
import kotlinx.serialization.Serializable

@Serializable
object PecsFlowScreens

@Serializable
sealed class PecsFlowTypeHost {

    @Serializable
    data class PictogramSelection(val categoryId: Int) : PecsFlowTypeHost()

}

fun NavGraphBuilder.pecsFlowNavGraph(navController: NavController) {
    navigation<PecsFlowScreens>(startDestination = PecsFlowTypeHost.PictogramSelection(0)) {

        composable<PecsFlowTypeHost.PictogramSelection> {
            val pictogramSelectionFlowHost = it.toRoute<PecsFlowTypeHost.PictogramSelection>()
            val categoryId = pictogramSelectionFlowHost.categoryId

            PecsFlowPictogramSelection(categoryId = categoryId) {
                navController.popBackStack()
            }
        }
    }
}

fun NavController.navigateToPecsPictogramSelection(categoryModel: CategoryModel) {
    navigate(PecsFlowTypeHost.PictogramSelection(categoryModel.id.orZero))
}
