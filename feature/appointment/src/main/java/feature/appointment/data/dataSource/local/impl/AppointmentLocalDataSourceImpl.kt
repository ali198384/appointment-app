package feature.appointment.data.dataSource.local.impl

import core.common.model.TimeSlot
import core.database.local.dao.AppointmentDao
import core.database.local.dao.TimeSlotDao
import core.database.local.entity.AppointmentEntity
import core.database.local.entity.TimeSlotEntity
import core.database.model.SyncState
import feature.appointment.data.dataSource.local.AppointmentLocalDataSource
import feature.appointment.data.mapper.toDomain
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import javax.inject.Inject

class AppointmentLocalDataSourceImpl @Inject constructor(
    private val timeSlotDao: TimeSlotDao,
    private val appointmentDao: AppointmentDao,
) : AppointmentLocalDataSource {

    override suspend fun insertAppointment(entity: AppointmentEntity): Long =
        appointmentDao.insertAppointment(entity)

    override suspend fun getUnsynced(): List<AppointmentEntity> =
        appointmentDao.getPending()

    override suspend fun getAppointmentByTrackingCode(trackingCode: String) =
        appointmentDao.getAppointmentByTrackingCode(trackingCode)

    override suspend fun markAppointmentDeleted(trackingCode: String) {
        appointmentDao.markDeleted(trackingCode, SyncState.PENDING_DELETE)
    }

    override suspend fun deleteAppointmentAfterSync(id: Long) {
        appointmentDao.deleteAppointmentAfterSync(id)
    }

    override suspend fun insertAllTimeSlot(list: List<TimeSlotEntity>) {
        timeSlotDao.insertAllTimeSlot(list)
    }

    override suspend fun getTimeSlots(doctorId: Long): List<TimeSlotEntity> =
        timeSlotDao.getTimeSlots(doctorId)

    override suspend fun updateTimeSlotReserve(slotId: Long, isReserve: Boolean) {
        timeSlotDao.updateTimeSlotReserve(slotId, isReserve)
    }
}