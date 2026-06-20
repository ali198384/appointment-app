package feature.profile.data.di

import core.sync.manager.Syncable
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import feature.profile.data.sync.ProfileSync

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileSyncModule {

    @Binds
    @IntoSet
    abstract fun bindProfileSync(
        impl: ProfileSync
    ): Syncable
}
