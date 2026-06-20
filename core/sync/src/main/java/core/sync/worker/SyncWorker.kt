package core.sync.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import core.sync.manager.Syncable
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val syncables: Set<@JvmSuppressWildcards Syncable>
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result =
        runCatching {
            syncables.forEach { syncable ->
                syncable.sync()
            }
        }.fold(
            onSuccess = { Result.success() },
            onFailure = { Result.retry() }
        )
}