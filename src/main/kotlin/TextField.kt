import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextField(width: Dp) {

    var tfText by remember { mutableStateOf("") }
    var hover by remember { mutableStateOf(true) }

    Box(Modifier ) {
        BasicTextField(
            value = tfText,
            onValueChange = { tfText = it },
            singleLine = true,
            textStyle = TextStyle(BasicColors.textColor),
            modifier = Modifier
                .width(width)
                .height(26.dp)
//                .border(1.dp, if (hover) BasicColors.tertiaryBGColor else BasicColors.secondaryBGColor)
                .background(BasicColors.primaryBGColor)
                .padding(horizontal = 10.dp, vertical = 4.dp),
            decorationBox = {innerTextField ->
                innerTextField()
            }
        )
    }
//OutlinedTextField()

}