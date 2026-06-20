package feature.doctor.data.dataSource.remote.impl

import core.network.api.DoctorApi
import core.network.dto.DoctorDto
import feature.doctor.data.dataSource.remote.DoctorRemoteDataSource
import kotlinx.coroutines.delay
import javax.inject.Inject

class DoctorRemoteDataSourceImpl @Inject constructor(
    private val api: DoctorApi
) : DoctorRemoteDataSource {

    override suspend fun fetchDoctors(
        page: Int,
        size: Int,
        specialtyId: Long,
        query: String
    ): List<DoctorDto> = api.getDoctors(page, size, specialtyId, query)
}