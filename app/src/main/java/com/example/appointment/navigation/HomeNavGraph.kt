package com.example.appointment.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.appointment.home.HomeScreen
import core.ui.navigation.AppRoute

fun NavGraphBuilder.homeNavGraph(
    navController: NavController
) {
    profileNavGraph(navController)

    specialtyNavGraph(navController)

    composable(AppRoute.Home.route) {

        HomeScreen(
            onProfileClick = {
                navController.navigate(AppRoute.Profile.route)
            },
            onCurrentClick = {
                navController.navigate(
                    AppRoute.GetAppointment.route
                )
            },
            onGetAppointmentClick = {
                navController.navigate(
                    AppRoute.SpecialtyList.route
                )
            }
        )
    }
}
