import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.input.pointer.isPrimaryPressed
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp



@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BoxScope.RightInfoColumn(selectedItem: String) {

    var edit by remember { mutableStateOf(false) }
    var tfText by remember { mutableStateOf("") }

    val path = if (selectedItem != "") selectedItem.replace("preview", "info", true)
        .replace(".jpg", ".txt", true) else ""

    var info = if (selectedItem != "") DirManipulations.getInfo(path)  else ""



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
                modifier = Modifier.size(24.dp).clickable { DirManipulations.openDir(path) }
            )


            if (edit) Icon(
                imageVector = Icons.Rounded.Done,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.clickable {
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
                modifier = Modifier.clickable {
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