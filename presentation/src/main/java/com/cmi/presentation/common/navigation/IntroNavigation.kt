package com.cmi.presentation.common.navigation

import androidx.compose.ui.platform.LocalUriHandler
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.cmi.presentation.Constants.YOUTUBE_GUIDE
import com.cmi.presentation.intro.IntroScreen
import com.cmi.presentation.ktx.openURL
import com.cmi.presentation.survey.SurveyScreen
import kotlinx.serialization.Serializable

@Serializable
object IntroNavigationScreens

@Serializable
sealed class DefaultIntroTypeHost {
    @Serializable
    data object Default : CategoryConfigurationTypeHost()

    @Serializable
    data object Survey : CategoryConfigurationTypeHost()

}

fun NavGraphBuilder.introNavGraph(navController: NavController) {
    navigation<IntroNavigationScreens>(startDestination = DefaultIntroTypeHost.Default) {

        composable<DefaultIntroTypeHost.Default> {
            val uriHandler = LocalUriHandler.current
            IntroScreen(
                onSettingsSelected = {
                    navController.navigateToConfiguration()
                },
                onSurveySelected = {
                    navController.navigateToSurveyScreen()
                },
                onStartPecsFlow = {
                    navController.navigateToCategoryChooserPecsFlow()
                },
                onOpenGuide = {
                    openURL(uriHandler, YOUTUBE_GUIDE)
                }
            )
        }

        composable<DefaultIntroTypeHost.Survey> {
            SurveyScreen()
        }
    }
}

private fun NavController.navigateToSurveyScreen(){
    navigate(DefaultIntroTypeHost.Survey)
}