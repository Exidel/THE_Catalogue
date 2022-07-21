import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalMinimumTouchTargetEnforcement
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState


@OptIn(ExperimentalMaterialApi::class)
fun main() = application {

    val state = rememberWindowState(
        size = DirManipulations.loadSettings().winSize,
        position = DirManipulations.loadSettings().winPosition
    )

    CompositionLocalProvider(LocalMinimumTouchTargetEnforcement provides false) {

        Window(
            state = state,
            onCloseRequest = ::exitApplication,
            undecorated = true,
            icon = painterResource("catalogue.ico")
        ) {

            MaterialTheme {
                MainView(state) { exitApplication() }
            }

        }
    }

}