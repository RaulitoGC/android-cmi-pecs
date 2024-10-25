package com.cmi.presentation.common.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.cmi.presentation.config.ConfigurationRootScreen
import com.cmi.presentation.config.ConfigurationScreen
import com.cmi.presentation.config.FLOW
import com.cmi.presentation.config.category.common.CategoryChooser
import kotlinx.serialization.Serializable


@Serializable
object ConfigurationScreens

@Serializable
private object ConfigurationHost

@Serializable
private sealed class ConfigurationTypeHost {
    @Serializable
    data object Category : ConfigurationTypeHost()

    @Serializable
    data object Pictogram : ConfigurationTypeHost()
}

enum class ConfigurationFlowType {
    SELECT,
    ADD,
    EDIT,
    REMOVE
}

fun NavGraphBuilder.configurationNavGraph(navController: NavController) {
    navigation<ConfigurationScreens>(startDestination = ConfigurationHost) {
        composable<ConfigurationHost> {
            ConfigurationRootScreen(
                onBack = {
                    navController.popBackStack()
                },
                onItemSelected = { flow ->
                    when(flow){
                        FLOW.CATEGORY -> navController.navigate(ConfigurationTypeHost.Category)
                        FLOW.PICTOGRAM -> navController.navigate(ConfigurationTypeHost.Pictogram)
                    }

                }
            )
        }

        composable<ConfigurationTypeHost.Category> { _ ->
            ConfigurationScreen(
                configurationFlow = FLOW.CATEGORY,
                onBack = {
                    navController.popBackStack()
                },
                onItemSelected = { configurationTypeFlow ->
                    when(configurationTypeFlow){
                        ConfigurationFlowType.SELECT -> navController.navigate(CategoryConfigurationTypeHost.Select)
                        ConfigurationFlowType.ADD -> navController.navigate(CategoryConfigurationTypeHost.Add)
                        ConfigurationFlowType.EDIT -> navController.navigate(CategoryChooserHost.EditCategory)
                        ConfigurationFlowType.REMOVE -> navController.navigate(CategoryConfigurationTypeHost.Remove)
                    }
                }
            )
        }

        composable<ConfigurationTypeHost.Pictogram> { _ ->
            ConfigurationScreen(
                configurationFlow = FLOW.PICTOGRAM,
                onBack = {
                    navController.popBackStack()
                },
                onItemSelected = { configurationFlowType ->
                    when(configurationFlowType){
                        ConfigurationFlowType.SELECT -> navController.navigate(PictogramConfigurationTypeHost.Select)
                        ConfigurationFlowType.ADD -> navController.navigate(PictogramConfigurationTypeHost.Add)
                        ConfigurationFlowType.EDIT -> navController.navigate(CategoryChooserHost.EditPictogram)
                        ConfigurationFlowType.REMOVE -> navController.navigate(PictogramConfigurationTypeHost.Remove)
                    }
                }
            )
        }
    }
}
