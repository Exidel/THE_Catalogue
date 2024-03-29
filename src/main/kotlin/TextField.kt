import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextFieldWithMenu(
    indexLamb: (Int) -> Unit,
    tfText: String,
    tfTextLamb: (String) -> Unit,
    labels: Labels,
    mouseClickPosition: Offset
) {

/** TF variables */
    var focused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current


    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .shadow(8.dp, RoundedCornerShape(4.dp))
            .border(1.dp, if (focused) Color.White else BasicColors.tertiaryBGColor, RoundedCornerShape(4.dp))
            .onGloballyPositioned {
                it.boundsInWindow().let { _rect ->
                    if (!_rect.contains(mouseClickPosition) && focused) focusManager.clearFocus()
                }
            }
    ) {

        BasicTextField(
            value = tfText,
            onValueChange = { tfTextLamb(it)},
            singleLine = true,
            textStyle = Styles.textStyle,
            cursorBrush = Brush.linearGradient(listOf(Color.White, Color.White)),
            modifier = Modifier
                .width(170.dp)
                .height(26.dp)
                .background(BasicColors.primaryBGColor)
                .onFocusChanged { focused = it.isFocused }
                .onKeyEvent {
                    if ((it.key == Key.Enter) && (it.type == KeyEventType.KeyUp)) {

                        true
                    } else if ((it.key == Key.Escape) && (it.type == KeyEventType.KeyUp)) {
                        tfTextLamb("")
                        focusManager.clearFocus()
                        true
                    } else {false}
                }
                .padding(horizontal = 10.dp, vertical = 4.dp)
        )


        Divider(Modifier.fillMaxHeight().width(1.dp), BasicColors.tertiaryBGColor)

        DDMenu(labels.searchDDMenu, {indexLamb(it)}, 120.dp, RoundedCornerShape(0.dp), Dp.Unspecified, Dp.Unspecified)

    }



}