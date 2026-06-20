package core.database.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import core.database.local.entity.SpecialtyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SpecialtyDao {

    @Query("SELECT * FROM specialties WHERE LOWER(title) LIKE '%' || LOWER(:searchQuery) || '%' ORDER BY title")
    fun observeAll(searchQuery: String): Flow<List<SpecialtyEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<SpecialtyEntity>)
}
