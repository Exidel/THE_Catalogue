import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Menu
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DraggableArea(state: WindowState, close: () -> Unit) {

    var hover by remember { mutableStateOf(false) }
    var enableLOGOShadow by remember { mutableStateOf(false) }
    var openMenu by remember { mutableStateOf(false) }
    var menuIndex by remember { mutableStateOf(0) }


    Column {
        Box(Modifier.fillMaxWidth().height(48.dp).background(BasicColors.secondaryBGColor).padding(start = 10.dp)) {

            Icon(
                imageVector = Icons.Rounded.Menu,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable { openMenu = !openMenu }
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
                        .clickable { close.invoke() }
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