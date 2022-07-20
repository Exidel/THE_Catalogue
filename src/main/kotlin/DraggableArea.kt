import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import javax.swing.JFileChooser


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DraggableArea(
    state: WindowState,
    close: () -> Unit,
    langIndex: Int,
    langLamb: (Int) -> Unit,
    itemSize: Float
) {

    var hover by remember { mutableStateOf(false) }
    var enableLOGOShadow by remember { mutableStateOf(DirManipulations.loadSettings().logo) }
    var rootDirectory by remember { mutableStateOf("") }
    val fileChooser = JFileChooser()
        fileChooser.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY


    Column {
        Box(Modifier.fillMaxWidth().height(48.dp).background(BasicColors.secondaryBGColor)) {

            MainMenu(
                enable = enableLOGOShadow,
                enableLamb = {enableLOGOShadow = it},
                resetWindow = {
                    state.size = DpSize(1200.dp, 800.dp)
                    state.position = WindowPosition(Alignment.Center)
                },
                langIndex = langIndex,
                langLamb = langLamb,
                rootDirectory = {
                    fileChooser.showDialog(null, "Select")
                    rootDirectory = if (fileChooser.selectedFile != null) fileChooser.selectedFile.path else ""
                    DirManipulations.saveSettings(state, langIndex, enableLOGOShadow, itemSize, rootDirectory)
                },
                exit = {
                    DirManipulations.saveSettings(state, langIndex, enableLOGOShadow, itemSize, rootDirectory)
                    close.invoke()
                }
            )


            if (enableLOGOShadow) {
                Image(
                    painter = painterResource(resourcePath = "/catalogue.png"),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.DarkGray),
                    modifier = Modifier.padding(end = 8.dp, top = 8.dp).blur(0.1.dp).size(34.dp).align(Alignment.Center)
                ) // shadow for LOGO
            }


            Image(
                painter = painterResource(resourcePath = "/catalogue.png"),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier.size(34.dp).align(Alignment.Center)
            ) // LOGO


            Row(
                modifier = Modifier.align(Alignment.CenterEnd),
                horizontalArrangement = Arrangement.spacedBy(0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(Modifier.size(48.dp).clickable { state.isMinimized = true }) {
                    Icon(
                        painter = painterResource("/round_minimize_black_24dp.png"),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp).align(Alignment.Center)
                    )
                }


                Box(
                    Modifier
                        .size(48.dp)
                        .clickable {
                            state.placement =
                                if (state.placement == WindowPlacement.Maximized) WindowPlacement.Floating
                                else WindowPlacement.Maximized
                }) {
                    Icon(
                        painterResource(if (state.placement == WindowPlacement.Floating) "round_maximize_black_48dp.png"
                        else "round_maximized_black_48dp.png"),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(12.dp).align(Alignment.Center)
                    )
                }


                Box(
                    Modifier.size(48.dp)
                        .background(if (hover) Color(1f, 0.4f, 0.4f, 1f) else BasicColors.secondaryBGColor)
                        .clickable {
                            DirManipulations.saveSettings(state, langIndex, enableLOGOShadow, itemSize, rootDirectory)
                            close.invoke()
                        }
                        .pointerMoveFilter(
                            onEnter = { hover = true; false },
                            onExit = { hover = false; false }
                        )
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }


            }
        }

        Divider(Modifier.shadow(8.dp), BasicColors.tertiaryBGColor, 1.dp)
    }



}