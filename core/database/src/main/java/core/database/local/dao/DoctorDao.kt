package core.database.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import core.database.local.entity.DoctorEntity
import core.database.local.entity.DoctorSpecialtyCrossRef
import core.database.local.relation.DoctorWithRelations

@Dao
interface DoctorDao {

    @Query("""
        SELECT d.*
        FROM doctors d
        INNER JOIN doctor_specialty_cross_ref ds
        ON d.id = ds.doctorId
        WHERE ds.specialtyId = :specialtyId
        AND d.name LIKE '%' || :query || '%'
        ORDER BY d.name
    """)
    fun getDoctorsPaging(
        specialtyId: Long,
        query: String
    ): PagingSource<Int, DoctorEntity>

    @Transaction
    @Query("SELECT * FROM doctors WHERE id = :doctorId")
    suspend fun getDoctorWithRelations(doctorId: Long): DoctorWithRelations


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDoctors(items: List<DoctorEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrossRefs(list: List<DoctorSpecialtyCrossRef>)

    @Query("DELETE FROM doctors")
    suspend fun clearDoctors()
}
