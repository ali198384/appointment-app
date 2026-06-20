package feature.specialty.data.dataSource.local.impl

import core.database.local.dao.SpecialtyDao
import core.database.local.entity.SpecialtyEntity
import feature.specialty.data.dataSource.local.SpecialtyLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SpecialtyLocalDataSourceImpl @Inject constructor (
    private val dao: SpecialtyDao
) : SpecialtyLocalDataSource {
    override fun observeSpecialties(query: String): Flow<List<SpecialtyEntity>> =
        dao.observeAll(query)
    override suspend fun insertAll(items: List<SpecialtyEntity>) = dao.insertAll(items)
}