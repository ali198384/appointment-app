package core.common.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

fun <T> offlineFirstFlow(
    local: () -> Flow<T>,
    remote: suspend () -> T,
    saveRemote: suspend (T) -> Unit
): Flow<T> = flow {

    // همیشه اول local emit میکنیم
    emitAll(local())

}.onStart {

    // همزمان network refresh
    runCatching {
        val remoteData = remote()
        saveRemote(remoteData)
    }

    // وقتی saveRemote انجام بشه
    // Room خودش دوباره emit می‌کنه
}