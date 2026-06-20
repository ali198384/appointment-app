package com.example.appointment.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import core.ui.navigation.AppRoute
import feature.doctor.presentation.DoctorScreen

fun NavGraphBuilder.doctorNavGraph(
    navController: NavController
) {
    createAppointmentNavGraph(navController)
    composable(
        route = AppRoute.DoctorList.route,
        arguments = listOf(
            navArgument("specialtyId") {
                type = NavType.LongType
            }
        )
    ) { backStackEntry ->

        /*val id = backStackEntry.arguments?.getLong("specialtyId") ?: return@composable*/

        DoctorScreen(
            onDoctorClick = { doctorId ->
                navController.navigate(
                    AppRoute.CreateAppointment.create(doctorId)
                )
            },
            onBack = {
                navController.popBackStack()
            }
        )
    }
}