package feature.specialty.data.dataSource.remote

import core.network.dto.SpecialtyDto

interface SpecialtyRemoteDataSource {
    suspend fun fetchAll(): List<SpecialtyDto>
}
