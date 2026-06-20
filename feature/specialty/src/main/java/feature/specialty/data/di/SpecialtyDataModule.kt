package feature.specialty.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import feature.specialty.data.dataSource.local.SpecialtyLocalDataSource
import feature.specialty.data.dataSource.local.impl.SpecialtyLocalDataSourceImpl
import feature.specialty.data.dataSource.remote.SpecialtyRemoteDataSource
import feature.specialty.data.dataSource.remote.impl.SpecialtyRemoteDataSourceImpl
import feature.specialty.data.repository.SpecialtyRepositoryImpl
import feature.specialty.domain.repository.SpecialtyRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class SpecialtyDataModule {

    @Binds
    abstract fun bindRepository(
        impl: SpecialtyRepositoryImpl
    ): SpecialtyRepository

    @Binds
    abstract fun bindRemote(
        impl: SpecialtyRemoteDataSourceImpl
    ): SpecialtyRemoteDataSource

    @Binds
    abstract fun bindLocal(
        impl: SpecialtyLocalDataSourceImpl
    ): SpecialtyLocalDataSource
}