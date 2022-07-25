import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BoxScope.RightInfoColumn(selectedItem: String, mouseClickPosition: Offset) {

    var edit by remember { mutableStateOf(false) }
    var tfText by remember { mutableStateOf("") }

    val path = if (selectedItem != "") selectedItem.replace("preview", "info", true)
        .replace(".jpg", ".txt", true) else ""

    var info = if (selectedItem != "") DirManipulations.getInfo(path)  else ""
    val focusManager = LocalFocusManager.current
    var focused by remember { mutableStateOf(false) }



    Column(
        modifier = Modifier
            .align(Alignment.TopEnd)
            .width(250.dp)
            .fillMaxHeight()
            .background(BasicColors.secondaryBGColor)
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        Row(Modifier.align(Alignment.End), Arrangement.spacedBy(10.dp)) {

            Icon(
                painter = painterResource("folder.png"),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = false),
                        onClick = { DirManipulations.openDir(path) }
                    )
            )


            if (edit) Icon(
                imageVector = Icons.Rounded.Done,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = false)
                ) {
                    info = tfText
                    tfText = ""
                    DirManipulations.createInfo(info, path)
                    edit = false
                }
            )


            Icon(
                imageVector = if (edit) Icons.Rounded.Close else Icons.Rounded.Edit,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = false)
                ) {
                    edit = !edit
                    tfText = info
                }
            )
        }


        if (edit) {
            OutlinedTextField(
                value = tfText,
                onValueChange = { tfText = it },
                modifier = Modifier
                    .fillMaxHeight()
                    .border(1.dp, Color.Transparent, RoundedCornerShape(4.dp))
                    .background(Color.Transparent, RoundedCornerShape(8.dp))
                    .onFocusChanged { focused = it.isFocused }
                    .onGloballyPositioned {
                        it.boundsInWindow().let { _rect ->
                            if (!_rect.contains(mouseClickPosition) && focused) focusManager.clearFocus()
                        }
                    }
                    .onKeyEvent {
                        if ( it.isCtrlPressed && (it.key == Key.Enter) && (it.type == KeyEventType.KeyUp) ) {
                            info = tfText
                            tfText = ""
                            DirManipulations.createInfo(info, path)
                            edit = false
                            true
                        }
                        else if ((it.key == Key.Escape) && (it.type == KeyEventType.KeyUp)) {
                            tfText = ""
                            edit = false
                            true
                        } else {false}
                    },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = BasicColors.primaryBGColor,
                    backgroundColor = BasicColors.textColor
                )
            )
        } else {
            Text(
                text = info,
                style = Styles.textStyle,
                modifier = Modifier
                    .fillMaxSize()
                    .background(BasicColors.primaryBGColor, RoundedCornerShape(4.dp))
                    .border(2.dp, BasicColors.tertiaryBGColor, RoundedCornerShape(4.dp))
                    .padding(10.dp)
            )
        }

    }

}