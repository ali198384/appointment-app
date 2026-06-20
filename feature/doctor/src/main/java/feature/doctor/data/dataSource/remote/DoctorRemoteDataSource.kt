package feature.doctor.data.dataSource.remote

import core.network.dto.DoctorDto

interface DoctorRemoteDataSource {
    suspend fun fetchDoctors(
        page: Int,
        size: Int,
        specialtyId: Long,
        query: String
    ): List<DoctorDto>
}