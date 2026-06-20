package core.common.util

suspend inline fun <Remote, Local> offlineFirst(
    crossinline remote: suspend () -> Remote,
    crossinline saveRemote: suspend (Remote) -> Unit,
    crossinline local: suspend () -> Local
): Local = runCatching {

    val remoteData = remote()
    saveRemote(remoteData)
    local()

}.getOrElse {
    local()
}
