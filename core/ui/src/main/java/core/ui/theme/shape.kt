package core.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import com.intuit.sdp.R

@Composable
fun appShapes(): Shapes {

    return Shapes(
        extraSmall = RoundedCornerShape(
            dimensionResource(R.dimen._4sdp)
        ),
        small = RoundedCornerShape(
            dimensionResource(R.dimen._6sdp)
        ),

        medium = RoundedCornerShape(
            dimensionResource(R.dimen._8sdp)
        ),

        large = RoundedCornerShape(
            dimensionResource(R.dimen._12sdp)
        ),
        extraLarge = RoundedCornerShape(
            dimensionResource(R.dimen._16sdp)
        )
    )
}
