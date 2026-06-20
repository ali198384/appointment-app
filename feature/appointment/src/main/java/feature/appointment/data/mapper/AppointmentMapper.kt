package feature.appointment.data.mapper

import core.common.model.Appointment
import core.database.local.entity.AppointmentEntity
import core.database.local.relation.AppointmentWithRelations
import core.network.dto.AppointmentDto

fun AppointmentEntity.toDto(): AppointmentDto = AppointmentDto(
    timeSlotId = timeSlotId,
    patientName = patientName,
    patientNationalCode = patientNationalCode,
    patientMobileNumber = patientMobileNumber,
    patientAge = patientAge,
    patientIsMale = patientIsMale,
    trackingCode = trackingCode,
    isDeleted = isDeleted
)

fun AppointmentWithRelations.toDomain() =
    Appointment(
        timeSlot.timeSlot.id,
        appointment.patientName,
        appointment.trackingCode!!,
        timeSlot.timeSlot.dateTime,
        timeSlot.doctor.doctor.name,
        timeSlot.doctor.doctor.imageUrl,
        timeSlot.doctor.specialties.map { it.title }
    )

fun AppointmentDto.toEntity(trackingCode: String) =
    AppointmentEntity(
        timeSlotId = timeSlotId,
        patientName = patientName,
        patientNationalCode = patientNationalCode,
        patientMobileNumber = patientMobileNumber,
        patientAge = patientAge,
        patientIsMale = patientIsMale,
        trackingCode = trackingCode
    )
