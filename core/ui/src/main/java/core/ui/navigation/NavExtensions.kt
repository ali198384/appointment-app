package core.ui.navigation

import androidx.navigation.NavController

fun NavController.navigate(route: AppRoute) {
    navigate(route.route)
}

fun NavController.popToHome() {
    popBackStack(
        AppRoute.Home.route,
        inclusive = false
    )
}