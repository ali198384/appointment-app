package core.database.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import androidx.room.Transaction
import core.database.local.entity.AppointmentEntity
import core.database.model.SyncState
import core.database.local.relation.AppointmentWithRelations

@Dao
interface AppointmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppointment(entity: AppointmentEntity): Long

    @Query("SELECT * FROM appointments WHERE syncState != 'SYNCED'")
    suspend fun getPending(): List<AppointmentEntity>

    @Transaction
    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM appointments WHERE trackingCode = :code AND isDeleted = 0 LIMIT 1")
    suspend fun getAppointmentByTrackingCode(code: String): AppointmentWithRelations?

    @Query(
        """
                UPDATE appointments 
                SET isDeleted = 1, syncState = :state 
                WHERE trackingCode = :code
        """
    )
    suspend fun markDeleted(code: String, state: SyncState)

    @Query("DELETE FROM appointments WHERE id = :id")
    suspend fun deleteAppointmentAfterSync(id: Long)
}
