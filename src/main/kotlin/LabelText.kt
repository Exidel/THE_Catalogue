import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle


@Composable
fun LabelText(text: String, modifier: Modifier = Modifier) {

    Text(
        text = text,
        maxLines = 1,
        color = BasicColors.textColor,
        modifier = modifier,
        style = TextStyle(
            shadow = Shadow(
                color = Color.Black,
                offset = Offset(1f, 1f),
                blurRadius = 0.5f)
        )
    )

}