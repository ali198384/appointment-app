package feature.specialty.data.dataSource.remote.impl

import core.network.api.SpecialtyApi
import core.network.dto.DoctorDto
import core.network.dto.SpecialtyDto
import feature.specialty.data.dataSource.remote.SpecialtyRemoteDataSource
import kotlinx.coroutines.delay
import javax.inject.Inject

class SpecialtyRemoteDataSourceImpl @Inject constructor (
    private val api: SpecialtyApi
) : SpecialtyRemoteDataSource {

    override suspend fun fetchAll(): List<SpecialtyDto> = api.getSpecialties()
}
