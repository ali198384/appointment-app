package com.example.appointment.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import core.ui.navigation.AppRoute
import feature.specialty.presentation.SpecialtyScreen

fun NavGraphBuilder.specialtyNavGraph(
    navController: NavController
) {

    doctorNavGraph(navController)

    composable(AppRoute.SpecialtyList.route) {
        SpecialtyScreen(
            onNavigateToDoctors = { specialtyId ->
                navController.navigate(
                    AppRoute.DoctorList.create(specialtyId)
                )
            },
            onBack = {
                navController.popBackStack()
            }
        )
    }
}
