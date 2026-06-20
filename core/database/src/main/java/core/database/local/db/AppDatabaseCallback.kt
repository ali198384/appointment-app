package core.database.local.db

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import core.database.local.dao.DoctorDao
import core.database.local.dao.SpecialtyDao
import core.database.local.dao.TimeSlotDao
import core.database.local.entity.DoctorEntity
import core.database.local.entity.DoctorSpecialtyCrossRef
import core.database.local.entity.SpecialtyEntity
import core.database.local.entity.TimeSlotEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Provider

class AppDatabaseCallback(
    private val specialtyDaoProvider: Provider<SpecialtyDao>,
    private val doctorDaoProvider: Provider<DoctorDao>,
    private val timeSlotDaoProvider: Provider<TimeSlotDao>,
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob())
) : RoomDatabase.Callback() {

    private val dateList = mutableListOf(
        1746113400000L, 1746122400000L, 1746135000000L, 1746199800000L, 1746208800000L,
        1746221400000L, 1746286200000L, 1746295200000L, 1746307800000L, 1746372600000L,
        1746381600000L, 1746394200000L, 1746459000000L, 1746468000000L, 1746480600000L,
        1746545400000L, 1746554400000L, 1746567000000L, 1746631800000L, 1746640800000L,
        1746653400000L, 1746718200000L, 1746727200000L, 1746739800000L, 1746804600000L,
        1746813600000L, 1746826200000L, 1746891000000L, 1746900000000L, 1746912600000L
    )

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        scope.launch(Dispatchers.IO) {
            seed()
        }
    }

    private suspend fun seed() {
        seedSpecialties()
        seedDoctors()
        seedDoctorSpecialtyCrossRefs()
        seedSlots()
    }

    // ---------------------------
    // Specialties
    // ---------------------------

    private suspend fun seedSpecialties() {
        val list = listOf(
            SpecialtyEntity(1, "پزشک عمومی"),
            SpecialtyEntity(2, "قلب و عروق"),
            SpecialtyEntity(3, "پوست و مو"),
            SpecialtyEntity(4, "چشم پزشکی"),
            SpecialtyEntity(5, "ارتوپدی"),
            SpecialtyEntity(6, "روانپزشکی"),
            SpecialtyEntity(7, "اطفال")
        )
        specialtyDaoProvider.get().insertAll(list)
    }

    // ---------------------------
    // Doctors
    // ---------------------------

    private suspend fun seedDoctors() {

            val list = listOf(
                DoctorEntity(
                    id = 1,
                    name = "آرش امیری",
                    imageUrl = null
                ),
                DoctorEntity(
                    id = 2,
                    name = "نیلوفر محمدی",
                    imageUrl = null
                ),
                DoctorEntity(
                    id = 3,
                    name = "کیان رستمی",
                    imageUrl = null
                ),
                DoctorEntity(
                    id = 4,
                    name = "سارا حسینی",
                    imageUrl = null
                ),
                DoctorEntity(
                    id = 5,
                    name = "رامین کریمی",
                    imageUrl = null
                ),
                DoctorEntity(
                    id = 6,
                    name = "شهرزاد کاظمی",
                    imageUrl = null
                ),
                DoctorEntity(
                    id = 7,
                    name = "بابک مرادی",
                    imageUrl = null
                ),
                DoctorEntity(
                    id = 8,
                    name = "لیلا رضایی",
                    imageUrl = null
                ),
                DoctorEntity(
                    id = 9,
                    name = "سامان نیک\u200Cطلب",
                    imageUrl = null
                ),
                DoctorEntity(
                    id = 10,
                    name = "پریسا احمدی",
                    imageUrl = null
                )
            )
        doctorDaoProvider.get().insertDoctors(list)
    }

    // ---------------------------
    // seedDoctorSpecialtyCrossRefs
    // ---------------------------
    private suspend fun seedDoctorSpecialtyCrossRefs() {
        val list = listOf(
            DoctorSpecialtyCrossRef(1, 1),
            DoctorSpecialtyCrossRef(1, 3),
            DoctorSpecialtyCrossRef(2, 1),
            DoctorSpecialtyCrossRef(2, 2),
            DoctorSpecialtyCrossRef(3, 1),
            DoctorSpecialtyCrossRef(3, 5),
            DoctorSpecialtyCrossRef(4, 1),
            DoctorSpecialtyCrossRef(4, 5),
            DoctorSpecialtyCrossRef(4, 7),
            DoctorSpecialtyCrossRef(5, 1),
            DoctorSpecialtyCrossRef(5, 4),
            DoctorSpecialtyCrossRef(6, 2),
            DoctorSpecialtyCrossRef(6, 4),
            DoctorSpecialtyCrossRef(6, 6),
            DoctorSpecialtyCrossRef(7, 3),
            DoctorSpecialtyCrossRef(7, 4),
            DoctorSpecialtyCrossRef(8, 6),
            DoctorSpecialtyCrossRef(8, 7),
            DoctorSpecialtyCrossRef(9, 4),
            DoctorSpecialtyCrossRef(9, 5),
            DoctorSpecialtyCrossRef(10, 2),
            DoctorSpecialtyCrossRef(10, 5)
        )
        doctorDaoProvider.get().insertCrossRefs(list)
    }

    // ---------------------------
    // Time Slots
    // ---------------------------

    private suspend fun seedSlots() {

        val entity = mutableListOf<TimeSlotEntity>()

        for (i in 1..7) {
            entity.add(
                TimeSlotEntity(
                    i.toLong(),
                    1,
                    dateList[i - 1].toLocalDateTime(),
                    false
                )
            )
        }

        for (i in 8..16) {
            entity.add(
                TimeSlotEntity(
                    i.toLong(),
                    2,
                    dateList[i - 1].toLocalDateTime(),
                    false
                )
            )
        }

        for (i in 17..25) {
            entity.add(
                TimeSlotEntity(
                    i.toLong(),
                    3,
                    dateList[i - 1].toLocalDateTime(),
                    false
                )
            )
        }

        for (i in 26..30) {
            entity.add(
                TimeSlotEntity(
                    i.toLong(),
                    4,
                    dateList[i - 1].toLocalDateTime(),
                    false
                )
            )
        }

        timeSlotDaoProvider.get().insertAllTimeSlot(entity)
    }

    fun Long.toLocalDateTime(): LocalDateTime =
        Instant.ofEpochMilli(this)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
}