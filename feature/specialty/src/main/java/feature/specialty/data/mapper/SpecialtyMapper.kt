package feature.specialty.data.mapper

import core.database.local.entity.SpecialtyEntity
import core.network.dto.SpecialtyDto
import core.common.model.Specialty

fun SpecialtyDto.toDomain() = Specialty(id, title)

fun SpecialtyEntity.toDomain() = Specialty(id, title)

fun Specialty.toEntity() = SpecialtyEntity(id, title)
