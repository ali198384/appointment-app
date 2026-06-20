package core.common.util

suspend inline fun <T> localFirstWrite(
    crossinline local: suspend () -> T,
    crossinline remote: suspend () -> Unit,
    crossinline onError: suspend (Throwable) -> Unit
): T {

    val result = local() // اول DB

    runCatching {
        remote()         // بعد network
    }.onFailure {
        onError(it)      // مثلا mark as pending
    }

    return result
}

/*
override suspend fun deleteDoctor(id: Long) {

    localFirstWrite(

        local = {
            doctorDao.markDeleted(id) // soft delete
        },

        remote = {
            api.deleteDoctor(id)
            doctorDao.hardDelete(id)
        }
    )
}*/
