package com.cmi.presentation.common.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.cmi.presentation.ui.theme.CmiAppTheme

@Composable
fun Navigation() {
    CmiAppTheme {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = IntroNavigationScreens) {
            introNavGraph(navController)
            pecsFlowNavGraph(navController)
            configurationNavGraph(navController)
            categoryChooserNavGraph(navController)
            categoryConfigurationNavGraph(navController)
            pictogramConfigurationNavGraph(navController)
        }
    }
}
