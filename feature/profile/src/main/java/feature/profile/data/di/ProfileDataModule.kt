package feature.profile.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import feature.profile.data.dataSource.local.ProfileLocalDataSource
import feature.profile.data.dataSource.local.impl.ProfileLocalDataSourceImpl
import feature.profile.data.dataSource.remote.ProfileRemoteDataSource
import feature.profile.data.dataSource.remote.impl.FakeProfileRemoteDataSourceImpl
import feature.profile.data.repository.ProfileRepositoryImpl
import feature.profile.domain.repository.ProfileRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileDataModule {

    @Binds
    abstract fun bindRepository(
        impl: ProfileRepositoryImpl
    ): ProfileRepository

    @Binds
    abstract fun bindRemote(
        //در صورت داشتن سرور واقعی
        //impl: ProfileRemoteDataSourceImpl
        impl: FakeProfileRemoteDataSourceImpl
    ): ProfileRemoteDataSource

    @Binds
    abstract fun bindLocal(
        impl: ProfileLocalDataSourceImpl
    ): ProfileLocalDataSource
}
