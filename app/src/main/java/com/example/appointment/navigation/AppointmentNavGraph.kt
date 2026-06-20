package com.example.appointment.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import core.ui.navigation.AppRoute
import feature.appointment.presentation.create.CreateAppointmentScreen
import feature.appointment.presentation.find.FindAppointmentScreen
import feature.appointment.presentation.success.SuccessAppointmentScreen

fun NavGraphBuilder.createAppointmentNavGraph(
    navController: NavController
) {

    composable(
        route = AppRoute.CreateAppointment.route,
        arguments = listOf(
            navArgument("doctorId") {
                type = NavType.LongType
            }
        )
    ) {

        CreateAppointmentScreen(
            onSuccess = { trackingCode ->
                navController.navigate(AppRoute.SuccessAppointment.create(trackingCode))
            },
            onBack = { navController.popBackStack() }
        )
    }


    composable(
        route = AppRoute.SuccessAppointment.route,
        arguments = listOf(
            navArgument("trackingCode") {
                type = NavType.StringType
            }
        )
    ) {
        SuccessAppointmentScreen(
            onNavigateHome = {
                navController.navigate(AppRoute.Home.route) {
                    popUpTo(0)
                }
            }
        )
    }

    composable(AppRoute.GetAppointment.route) {
        FindAppointmentScreen(
            onBackHome = {
                navController.popBackStack()
            }
        )
    }
}
