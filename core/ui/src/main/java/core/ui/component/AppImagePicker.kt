package core.ui.component

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.activity.result.contract.ActivityResultContracts.TakePicturePreview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.intuit.sdp.R
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppImagePicker(
    imageUri: Uri?,
    onImagePicked: (Uri) -> Unit
) {

    val context = LocalContext.current
    var showSheet by remember { mutableStateOf(false) }

    /* ---------------- Launchers ---------------- */

    val galleryLauncher =
        rememberLauncherForActivityResult(GetContent()) { uri ->
            uri?.let(onImagePicked)
        }

    val cameraLauncher =
        rememberLauncherForActivityResult(TakePicturePreview()) { bitmap ->
            bitmap?.let { onImagePicked(it.toUri(context)) }
        }


    /* ================= AVATAR ================= */
    Box(
        modifier = Modifier,
        contentAlignment = Alignment.BottomEnd
    ) {

        AvatarContent(
            imageUri = imageUri,
            onClick = { showSheet = true }
        )

        SmallFloatingActionButton(
            onClick = { showSheet = true }
        ) {
            Icon(Icons.Default.CameraAlt, null)
        }
    }

    /* ================= SHEET ================= */
    val dimen20 = dimensionResource(R.dimen._20sdp)
    if (showSheet) {
        ModalBottomSheet(
            containerColor = MaterialTheme.colorScheme.surface,
            scrimColor = MaterialTheme.colorScheme.scrim.copy(alpha = 0.32f),
            onDismissRequest = { showSheet = false },
            shape = RoundedCornerShape(
                topStart = dimen20,
                topEnd = dimen20
            ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen._16sdp))
            ) {
                Text(
                    text = stringResource(core.ui.R.string.str_choose_image),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                AppSpacerHeight(R.dimen._12sdp)

                SheetItem(
                    icon = Icons.Default.Photo,
                    text = stringResource(core.ui.R.string.str_select_from_gallery)
                ) {
                    showSheet = false
                    galleryLauncher.launch("image/*")
                }

                SheetItem(
                    icon = Icons.Default.CameraAlt,
                    text = stringResource(core.ui.R.string.str_select_from_camera)
                ) {
                    showSheet = false
                    cameraLauncher.launch(null)
                }
            }
        }
    }
}


@Composable
private fun AvatarContent(
    imageUri: Uri?,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(dimensionResource(R.dimen._120sdp))
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .border(
                dimensionResource(R.dimen._1sdp),
                MaterialTheme.colorScheme.outline,
                CircleShape
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {

        if (imageUri != null) {
            AsyncImage(
                model = imageUri,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Icon(
                modifier = Modifier.size(dimensionResource(R.dimen._42sdp)),
                imageVector = Icons.Default.CameraAlt,
                contentDescription = null
            )
        }
    }
}


@Composable
private fun SheetItem(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .clickable { onClick() }
            .padding(vertical = dimensionResource(R.dimen._8sdp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        WidthSpacer10()
        Icon(icon, null)
        WidthSpacer10()
        Text(
            text,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun WidthSpacer10() = AppSpacerWidth(R.dimen._10sdp)

private fun Bitmap.toUri(context: Context): Uri {

    val file = File(context.filesDir, "profile.jpg")

    FileOutputStream(file).use {
        compress(Bitmap.CompressFormat.JPEG, 95, it)
    }

    return Uri.fromFile(file)
}
