package core.common.util

object TrackingCodeGenerator {

    fun generate(): String {

        val timestamp = System.currentTimeMillis()
        val random = (100..999).random()

        return (timestamp.toString().takeLast(7) + random)
    }
}
