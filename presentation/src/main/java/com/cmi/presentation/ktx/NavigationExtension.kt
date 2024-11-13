package com.cmi.presentation.ktx

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

inline fun <reified T: Any> NavGraphBuilder.defaultLeftToRightComposable(
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) = composable<T>(
    enterTransition = {
        enterSlidingToLeft()
    },
    popEnterTransition = {
        noEnterTransition()
    },
    exitTransition = {
        noExitTransition()
    },
    popExitTransition = {
        existSlidingToRight()
    }
) { navBackStackEntry ->
    content(navBackStackEntry)
}