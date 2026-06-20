package feature.doctor.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import feature.doctor.data.dataSource.local.DoctorLocalDataSource
import feature.doctor.data.dataSource.local.impl.DoctorLocalDataSourceImpl
import feature.doctor.data.dataSource.remote.DoctorRemoteDataSource
import feature.doctor.data.dataSource.remote.impl.DoctorRemoteDataSourceImpl
import feature.doctor.data.repository.DoctorRepositoryImpl
import feature.doctor.domain.repository.DoctorRepository
import feature.doctor.domain.repository.DoctorSharedRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class DoctorDataModule {

    @Binds
    abstract fun bindSharedRepository(
        impl: DoctorRepositoryImpl
    ): DoctorSharedRepository

    @Binds
    abstract fun bindRepository(
        impl: DoctorRepositoryImpl
    ): DoctorRepository

    @Binds
    abstract fun bindRemote(
        impl: DoctorRemoteDataSourceImpl
    ): DoctorRemoteDataSource

    @Binds
    abstract fun bindLocal(
        impl: DoctorLocalDataSourceImpl
    ): DoctorLocalDataSource
}
