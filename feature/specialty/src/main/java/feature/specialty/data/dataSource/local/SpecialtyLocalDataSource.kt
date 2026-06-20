package feature.specialty.data.dataSource.local

import core.database.local.entity.SpecialtyEntity
import kotlinx.coroutines.flow.Flow

interface SpecialtyLocalDataSource {
    fun observeSpecialties(query: String): Flow<List<SpecialtyEntity>>
    suspend fun insertAll(items: List<SpecialtyEntity>)
}