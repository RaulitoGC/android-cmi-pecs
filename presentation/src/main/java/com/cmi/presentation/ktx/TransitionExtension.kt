package com.cmi.presentation.ktx

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavBackStackEntry

fun AnimatedContentTransitionScope<NavBackStackEntry>.enterSlidingToUp() = slideIntoContainer(
    towards = AnimatedContentTransitionScope.SlideDirection.Up,
    animationSpec = tween(durationMillis = 1000)
)

fun AnimatedContentTransitionScope<NavBackStackEntry>.enterSlidingToLeft() = slideIntoContainer(
    towards = AnimatedContentTransitionScope.SlideDirection.Left,
    animationSpec = tween(durationMillis = 1000)
)

fun AnimatedContentTransitionScope<NavBackStackEntry>.existSlidingToRight() = slideOutOfContainer(
    towards = AnimatedContentTransitionScope.SlideDirection.Right,
    animationSpec = tween(durationMillis = 1000)
)

fun AnimatedContentTransitionScope<NavBackStackEntry>.popExistSlidingToDown() = slideOutOfContainer(
    towards = AnimatedContentTransitionScope.SlideDirection.Down,
    animationSpec = tween(durationMillis = 1000)
)

fun AnimatedContentTransitionScope<NavBackStackEntry>.noEnterTransition() = slideIn(
    initialOffset = { IntOffset(0, 0) },
)

fun AnimatedContentTransitionScope<NavBackStackEntry>.noExitTransition() = slideOut(
    targetOffset = { IntOffset(0, 0) },
)