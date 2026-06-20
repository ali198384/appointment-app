package core.ui.navigation


sealed class AppRoute(val route: String) {

    // -------------------------
    // Home
    // -------------------------
    data object Home : AppRoute("home")


    // -------------------------
    // Profile
    // -------------------------
    data object Profile : AppRoute("profile")


    // -------------------------
    // Specialty
    // -------------------------
    data object SpecialtyList : AppRoute("specialty_list")


    // -------------------------
    // Doctors
    // -------------------------
    data object DoctorList : AppRoute("doctor_list/{specialtyId}") {
        fun create(specialtyId: Long) = "doctor_list/$specialtyId"
    }


    // -------------------------
    // Appointment
    // -------------------------
    data object CreateAppointment : AppRoute("create_appointment/{doctorId}") {
        fun create(doctorId: Long) = "create_appointment/$doctorId"
    }

    data object SuccessAppointment : AppRoute("success_appointment/{trackingCode}") {
        fun create(trackingCode: String) = "success_appointment/$trackingCode"
    }

    data object GetAppointment : AppRoute("get_appointment")
}
