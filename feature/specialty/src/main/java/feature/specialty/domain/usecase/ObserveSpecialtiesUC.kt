package feature.specialty.domain.usecase

import core.common.model.Specialty
import feature.specialty.domain.repository.SpecialtyRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveSpecialtiesUC @Inject constructor(
    private val repository: SpecialtyRepository
) {
    @OptIn(FlowPreview::class)
    operator fun invoke(query: String): Flow<List<Specialty>> =
        repository.observeSpecialties(query)
}
