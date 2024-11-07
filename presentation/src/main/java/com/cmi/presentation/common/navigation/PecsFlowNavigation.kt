package com.cmi.presentation.common.navigation

import androidx.compose.material.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.cmi.presentation.ktx.orZero
import com.cmi.presentation.model.CategoryModel
import kotlinx.serialization.Serializable

@Serializable
object PecsFlowScreens

@Serializable
sealed class PecsFlowTypeHost {

    @Serializable
    data class PictogramSelection(val categoryId: Int) : PecsFlowTypeHost()

    @Serializable
    data object Tape : PecsFlowTypeHost()
}

fun NavGraphBuilder.pecsFlowNavGraph(navController: NavController) {
    navigation<PecsFlowScreens>(startDestination = PecsFlowTypeHost.PictogramSelection(0)) {

        composable<PecsFlowTypeHost.PictogramSelection> {
            Text("In developemnt")
        }

        composable<PecsFlowTypeHost.Tape> {
            Text("In developemnt")
        }

    }
}

fun NavController.navigateToPecsPictogramSelection(categoryModel: CategoryModel) {
    navigate(PecsFlowTypeHost.PictogramSelection(categoryModel.id.orZero))
}

fun NavController.navigateToTape() {
    navigate(PecsFlowTypeHost.Tape)
}