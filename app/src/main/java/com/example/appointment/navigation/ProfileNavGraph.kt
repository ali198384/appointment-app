package com.example.appointment.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import core.ui.navigation.AppRoute
import feature.profile.presentation.ProfileScreen

fun NavGraphBuilder.profileNavGraph(
    navController: NavController
) {
    composable(AppRoute.Profile.route) {
        ProfileScreen(
            onBack = {
                navController.popBackStack()
            }
        )
    }
}
