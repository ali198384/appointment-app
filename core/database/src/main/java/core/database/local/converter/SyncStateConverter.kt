package core.database.local.converter

import androidx.room.TypeConverter
import core.database.model.SyncState

class SyncStateConverter {
    @TypeConverter
    fun from(state: SyncState): String = state.name

    @TypeConverter
    fun to(value: String): SyncState = SyncState.valueOf(value)
}