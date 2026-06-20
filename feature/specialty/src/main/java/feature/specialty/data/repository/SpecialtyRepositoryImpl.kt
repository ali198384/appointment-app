package feature.specialty.data.repository

import feature.specialty.data.dataSource.local.SpecialtyLocalDataSource
import feature.specialty.data.dataSource.remote.SpecialtyRemoteDataSource
import feature.specialty.data.mapper.toDomain
import feature.specialty.data.mapper.toEntity
import core.common.model.Specialty
import core.common.util.offlineFirstFlow
import feature.specialty.domain.repository.SpecialtyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SpecialtyRepositoryImpl @Inject constructor(
    private val local: SpecialtyLocalDataSource,
    private val remote: SpecialtyRemoteDataSource
) : SpecialtyRepository {

    override fun observeSpecialties(query: String): Flow<List<Specialty>> =
        offlineFirstFlow(
            local = {
                local.observeSpecialties(query).map { list -> list.map { it.toDomain() } }
            },
            remote = {
                remote.fetchAll().map { it.toDomain() }
            },
            saveRemote = { remoteItems ->
                local.insertAll(
                    remoteItems.map { it.toEntity() }
                )
            }
        )
}