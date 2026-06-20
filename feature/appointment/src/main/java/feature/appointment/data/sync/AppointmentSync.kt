package feature.appointment.data.sync

import core.database.model.SyncState
import core.sync.manager.Syncable
import feature.appointment.data.dataSource.local.AppointmentLocalDataSource
import feature.appointment.data.dataSource.remote.AppointmentRemoteDataSource
import feature.appointment.data.mapper.toDto
import feature.appointment.data.repository.AppointmentRepositoryImpl
import feature.appointment.domain.repository.AppointmentRepository
import javax.inject.Inject


class AppointmentSync @Inject constructor(
    private val reop: AppointmentRepositoryImpl,
) : Syncable {

    override suspend fun sync() {
        reop.syncIfNeeded()
    }
}
