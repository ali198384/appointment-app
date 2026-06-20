package core.sync.file

/*
import java.io.File
import javax.inject.Inject

class FileManager @Inject constructor() {

    fun deleteImage(path: String) {

    }
}
*/

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FileManager @Inject constructor(
    @param:ApplicationContext private val context: Context
) {

    private fun profileFile() =
        File(context.filesDir, "profile.jpg")

    // copy content:// → internal file
    fun copyToInternal(uri: Uri): String {

        val file = profileFile()

        context.contentResolver.openInputStream(uri)?.use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }

        return file.absolutePath
    }

    // پاک کردن عکس قبلی
    fun deleteImage(path: String) {
        profileFile().delete()
        try {
            File(path).takeIf { it.exists() }?.delete()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
