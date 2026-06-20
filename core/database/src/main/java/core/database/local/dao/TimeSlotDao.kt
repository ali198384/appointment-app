package core.database.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import core.database.local.entity.TimeSlotEntity
import java.time.LocalDateTime

@Dao
interface TimeSlotDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTimeSlot(list: List<TimeSlotEntity>)

    @Query("""
        SELECT * FROM time_slots
        WHERE doctorId = :doctorId
        AND reserved = 0
        ORDER BY dateTime
    """)
    fun getTimeSlots(doctorId: Long): List<TimeSlotEntity>

    @Query("UPDATE time_slots SET reserved = :isReserve WHERE id = :id")
    suspend fun updateTimeSlotReserve(id: Long, isReserve: Boolean)
}
