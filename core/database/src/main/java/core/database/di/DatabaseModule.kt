package core.database.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import core.database.local.dao.DoctorDao
import core.database.local.dao.SpecialtyDao
import core.database.local.dao.TimeSlotDao
import core.database.local.db.AppDatabase
import core.database.local.db.AppDatabase.Companion.DATABASE_NAME
import core.database.local.db.AppDatabaseCallback
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        specialtyDaoProvider: Provider<SpecialtyDao>,
        doctorDaoProvider: Provider<DoctorDao>,
        timeSlotDaoProvider: Provider<TimeSlotDao>
    ): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME
        )
            .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
            .addCallback(
                AppDatabaseCallback(
                    specialtyDaoProvider,
                    doctorDaoProvider,
                    timeSlotDaoProvider
                )
            )
            .build()

    @Provides
    fun provideProfileDao(db: AppDatabase) = db.profileDao()

    @Provides
    fun provideSpecialtyDao(db: AppDatabase) = db.specialtyDao()

    @Provides
    fun provideDoctorDao(db: AppDatabase) = db.doctorDao()

    @Provides
    fun provideSlotDao(db: AppDatabase) = db.slotDao()

    @Provides
    fun provideAppointmentDao(db: AppDatabase) = db.appointmentDao()
}
