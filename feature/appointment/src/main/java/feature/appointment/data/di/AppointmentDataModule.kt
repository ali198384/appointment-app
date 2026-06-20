package feature.appointment.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import feature.appointment.data.dataSource.remote.AppointmentRemoteDataSource
import feature.appointment.data.dataSource.local.AppointmentLocalDataSource
import feature.appointment.data.dataSource.local.impl.AppointmentLocalDataSourceImpl
import feature.appointment.data.dataSource.remote.impl.AppointmentRemoteDataSourceImpl
import feature.appointment.data.repository.AppointmentRepositoryImpl
import feature.appointment.domain.repository.AppointmentRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class AppointmentDataModule {

    @Binds
    abstract fun bindRepository(
        impl: AppointmentRepositoryImpl
    ): AppointmentRepository

    @Binds
    abstract fun bindRemote(
        impl: AppointmentRemoteDataSourceImpl
    ): AppointmentRemoteDataSource

    @Binds
    abstract fun bindLocal(
        impl: AppointmentLocalDataSourceImpl
    ): AppointmentLocalDataSource
}
