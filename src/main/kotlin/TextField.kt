import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.input.key.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.rememberCursorPositionProvider
import org.jetbrains.skiko.Cursor


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextFieldWithMenu() {

    var tfText by remember { mutableStateOf("") }
    var hover by remember { mutableStateOf(false) }
    val focus = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current


    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .shadow(8.dp, RoundedCornerShape(4.dp))
            .border(1.dp, if (hover) Color.White else BasicColors.tertiaryBGColor, RoundedCornerShape(4.dp))
//            .pointerMoveFilter(
//                onEnter = { hover = true; false },
//                onExit = { hover = false; false }
//            )
    ) {

        BasicTextField(
            value = tfText,
            onValueChange = { tfText = it },
            singleLine = true,
            textStyle = Styles.textStyle,
            cursorBrush = Brush.linearGradient(listOf(Color.White, Color.White)),
            modifier = Modifier
                .width(170.dp)
                .height(26.dp)
                .background(BasicColors.primaryBGColor)
                .focusRequester(focus)
                .onFocusChanged { hover = it.isFocused }
                .onKeyEvent {
                    if ((it.key == Key.Enter) && (it.type == KeyEventType.KeyUp)) {

                        true
                    } else if ((it.key == Key.Escape) && (it.type == KeyEventType.KeyUp)) {
                        tfText = ""
                        focusManager.clearFocus()
                        true
                    } else {false}
                }
                .padding(horizontal = 10.dp, vertical = 4.dp)
        )


        Divider(Modifier.fillMaxHeight().width(1.dp), BasicColors.tertiaryBGColor)

        DDMenu(Labels().searchDDMenu, {}, 120.dp, RoundedCornerShape(0.dp), Dp.Unspecified, Dp.Unspecified)

    }



}