import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.rememberDialogState



@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DeleteDialog(
    isOpenLamb: (Boolean) -> Unit,
    labels: Labels,
    message: String,
    delete: () -> Unit
) {

    val scrollState = rememberScrollState()
    val dialogState = rememberDialogState()

    Dialog(
        onCloseRequest = { isOpenLamb(false) },
        state = dialogState,
        undecorated = true,
        transparent = true,
        resizable = false,
        onKeyEvent = { if (it.key == Key.Escape && it.type == KeyEventType.KeyUp) isOpenLamb(false); true}
    ) {

        WindowDraggableArea {

            Box(Modifier.background(Color.Transparent)) {

                Box(
                    Modifier
                        .padding(top = 24.dp, bottom = 12.dp)
                        .background(BasicColors.primaryBGColor, RoundedCornerShape(8.dp))
                        .border(2.dp, BasicColors.tertiaryBGColor, RoundedCornerShape(8.dp))
                        .padding(start = 20.dp, end = 20.dp, top = 32.dp, bottom = 32.dp)
                ) {

                    Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {

                        Text(labels.message, style = Styles.textStyle, textAlign = TextAlign.Justify, modifier = Modifier.align(Alignment.CenterHorizontally))

                        Box(Modifier.verticalScroll(scrollState)) {
                            Text(message, style = Styles.textStyle, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                        }

                    }
                }


                Icon(
                    Icons.Rounded.Delete,
                    null,
                    Modifier
                        .align(Alignment.TopCenter)
                        .size(48.dp)
                        .background(BasicColors.primaryBGColor, CircleShape)
                        .border(2.dp, BasicColors.tertiaryBGColor, CircleShape)
                        .padding(5.dp),
                    Color.White
                )


                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                ) {
                    Box(
                        Modifier
                            .size(100.dp, 24.dp)
                            .background(Color(0.2f, 0.6f, 0.2f, 1f), RoundedCornerShape(4.dp))
                            .border(2.dp, BasicColors.tertiaryBGColor, RoundedCornerShape(4.dp))
                            .clickable { delete.invoke(); isOpenLamb(false) }
                            .padding(10.dp, 5.dp)
                    ) {
                        Text(labels.yes, style = Styles.textStyle, modifier = Modifier.align(Alignment.Center))
                    }

                    Box(
                        Modifier
                            .size(100.dp, 24.dp)
                            .background(Color(0.6f, 0.2f, 0.2f, 1f), RoundedCornerShape(4.dp))
                            .border(2.dp, BasicColors.tertiaryBGColor, RoundedCornerShape(4.dp))
                            .clickable { isOpenLamb(false) }
                            .padding(10.dp, 5.dp)
                    ) {
                        Text(labels.no, style = Styles.textStyle, modifier = Modifier.align(Alignment.Center))
                    }
                }

            }

        }

    }

}