package feature.appointment.data.repository

import androidx.room.withTransaction
import core.common.model.Appointment
import core.common.model.Doctor
import core.common.model.Patient
import core.common.model.Profile
import core.common.model.TimeSlot
import core.common.util.TrackingCodeGenerator
import core.common.util.localFirstWrite
import core.common.util.offlineFirst
import core.database.local.db.AppDatabase
import core.database.model.SyncState
import core.network.dto.AppointmentDto
import feature.appointment.data.dataSource.local.AppointmentLocalDataSource
import feature.appointment.data.dataSource.remote.AppointmentRemoteDataSource
import feature.appointment.data.mapper.toDomain
import feature.appointment.data.mapper.toDto
import feature.appointment.data.mapper.toEntity
import feature.appointment.domain.repository.AppointmentRepository
import feature.doctor.domain.repository.DoctorSharedRepository
import feature.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class AppointmentRepositoryImpl @Inject constructor(
    private val db: AppDatabase,
    private val remote: AppointmentRemoteDataSource,
    private val local: AppointmentLocalDataSource,
    private val profileRepo: ProfileRepository,
    private val doctorRepo: DoctorSharedRepository
) : AppointmentRepository {

    override suspend fun getDoctorAndSpecialties(doctorId: Long): Doctor =
        doctorRepo.getDoctorWithRelations(doctorId)

    override suspend fun getSlots(doctorId: Long): List<TimeSlot> =
        offlineFirst(
            remote = {
                remote.getTimeSlots(doctorId)
            },
            saveRemote = { mRemote ->
                local.insertAllTimeSlot(mRemote.map { it.toEntity() })
            },
            local = {
                local.getTimeSlots(doctorId).filter { !it.reserved }.map { it.toDomain() }
            }
        )

    override suspend fun getProfile(): Profile? =
        profileRepo.getProfile()

    override suspend fun createAppointment(timeSlotId: Long, patient: Patient): String {

        var appointmentDto = AppointmentDto(
            timeSlotId = timeSlotId,
            patientName = patient.name,
            patientNationalCode = patient.nationalCode,
            patientMobileNumber = patient.mobileNumber,
            patientAge = patient.age,
            patientIsMale = patient.isMale,
        )

        return runCatching {
            remote.createAppointment(appointmentDto)
        }.map { trackingCode ->
            appointmentDto = appointmentDto.copy(trackingCode = trackingCode)
            local.updateTimeSlotReserve(timeSlotId, true)
            trackingCode!!
        }.getOrElse {

            // حالت بدون سرور
            val trackingCode = TrackingCodeGenerator.generate()

            db.withTransaction {
                local.updateTimeSlotReserve(timeSlotId, true)
                local.insertAppointment(appointmentDto.toEntity(trackingCode))
            }

            trackingCode // در حالت با سرور خطا برگرداند
        }
    }

    override suspend fun syncIfNeeded() {
        val pending = local.getUnsynced()
        runCatching {
            pending.forEach { entity ->

                val success = when (entity.syncState) {
                    //در صورت داشتن سرور باید یک نوتیفیکشن برای کد رهگیری به کاربر نشان داد
                    SyncState.PENDING_INSERT ->
                        runCatching {
                            !remote.createAppointment(entity.toDto()).isNullOrBlank()
                        }.getOrElse { false }

                    SyncState.PENDING_DELETE ->
                        runCatching {
                            remote.deleteAppointment(entity.trackingCode!!)
                        }.getOrElse { false }

                    else -> false
                }

                if (success) {
                    local.deleteAppointmentAfterSync(entity.id)
                }
            }
        }
    }

    override suspend fun getAppointmentByTrackingCode(trackingCode: String): Appointment? =
        offlineFirst(
            remote = {
                remote.getByTrackingCode(trackingCode)
            },
            saveRemote = { mRemote ->
                mRemote?.let {
                    local.insertAppointment(it.toEntity(trackingCode))
                }
            },
            local = {
                local.getAppointmentByTrackingCode(trackingCode)?.toDomain()
            }
        )

    override suspend fun markAppointmentDeleted(trackingCode: String, timeSlotId: Long) =
        localFirstWrite(

            local = {
                db.withTransaction {
                    local.updateTimeSlotReserve(timeSlotId, false)
                    local.markAppointmentDeleted(trackingCode)
                }
            },

            remote = {
                syncIfNeeded()
            },
            {}
        )
}
