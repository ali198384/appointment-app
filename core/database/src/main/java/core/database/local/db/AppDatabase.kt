package core.database.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import core.database.local.converter.DateTimeConverter
import core.database.local.converter.SyncStateConverter
import core.database.local.dao.AppointmentDao
import core.database.local.dao.DoctorDao
import core.database.local.dao.ProfileDao
import core.database.local.dao.TimeSlotDao
import core.database.local.dao.SpecialtyDao
import core.database.local.entity.AppointmentEntity
import core.database.local.entity.DoctorEntity
import core.database.local.entity.DoctorSpecialtyCrossRef
import core.database.local.entity.ProfileEntity
import core.database.local.entity.TimeSlotEntity
import core.database.local.entity.SpecialtyEntity

@Database(
    entities = [
        DoctorEntity::class,
        TimeSlotEntity::class,
        ProfileEntity::class,
        SpecialtyEntity::class,
        AppointmentEntity::class,
        DoctorSpecialtyCrossRef::class
    ],
    version = 1
)
@TypeConverters(
    SyncStateConverter::class,
    DateTimeConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun specialtyDao(): SpecialtyDao
    abstract fun doctorDao(): DoctorDao
    abstract fun slotDao(): TimeSlotDao
    abstract fun appointmentDao(): AppointmentDao

    companion object {
        const val DATABASE_NAME = "hospital_db"
    }
}