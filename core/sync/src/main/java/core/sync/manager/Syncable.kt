package core.sync.manager

interface Syncable {
    suspend fun sync()
}
