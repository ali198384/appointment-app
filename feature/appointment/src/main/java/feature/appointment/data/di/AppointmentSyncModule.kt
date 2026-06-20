package feature.appointment.data.di

import core.sync.manager.Syncable
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import feature.appointment.data.sync.AppointmentSync

@Module
@InstallIn(SingletonComponent::class)
abstract class AppointmentSyncModule {

    @Binds
    @IntoSet
    abstract fun bindAppointmentSync(
        impl: AppointmentSync
    ): Syncable
}
