import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.WindowState


@Composable
fun FrameWindowScope.MainView(state: WindowState, exitApp: () -> Unit) {

    Box(Modifier.fillMaxSize().background(BasicColors.secondaryBGColor)) {

        Column {

            WindowDraggableArea { DraggableArea(state) { exitApp.invoke() } }

            TopBar()

            Divider(color = BasicColors.tertiaryBGColor, modifier = Modifier.shadow(8.dp))

            Box(Modifier.fillMaxSize()) {
                LeftNavigationColumn()
                MiddleGrid()
                RightInfoColumn(Modifier.align(Alignment.TopEnd))
            }

        }

    }

}