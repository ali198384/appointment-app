package feature.specialty.domain.repository

import core.common.model.Specialty
import kotlinx.coroutines.flow.Flow

interface SpecialtyRepository {
    fun observeSpecialties(query: String): Flow<List<Specialty>>
}

