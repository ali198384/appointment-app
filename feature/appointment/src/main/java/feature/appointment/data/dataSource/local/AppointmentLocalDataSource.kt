package feature.appointment.data.dataSource.local

import core.common.model.TimeSlot
import core.database.local.entity.AppointmentEntity
import core.database.local.entity.TimeSlotEntity
import core.database.model.SyncState
import core.database.local.relation.AppointmentWithRelations
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface AppointmentLocalDataSource {
    suspend fun insertAppointment(entity: AppointmentEntity): Long
    suspend fun getUnsynced(): List<AppointmentEntity>
    suspend fun getAppointmentByTrackingCode(trackingCode: String): AppointmentWithRelations?
    suspend fun markAppointmentDeleted(trackingCode: String)
    suspend fun deleteAppointmentAfterSync(id: Long)
    suspend fun insertAllTimeSlot(list: List<TimeSlotEntity>)
    suspend fun getTimeSlots(doctorId: Long): List<TimeSlotEntity>
    suspend fun updateTimeSlotReserve(slotId: Long, isReserve: Boolean)
}