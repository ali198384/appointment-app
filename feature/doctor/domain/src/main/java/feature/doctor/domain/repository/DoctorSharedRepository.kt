package feature.doctor.domain.repository

import core.common.model.Doctor

interface DoctorSharedRepository {
    suspend fun getDoctorWithRelations(doctorId: Long): Doctor
}