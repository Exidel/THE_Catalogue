import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp


@Composable
fun MainView() {

    Box(Modifier.fillMaxSize().background(BasicColors.secondaryBGColor)) {

        Column {

            TopBar()

            Divider(color = BasicColors.tertiaryBGColor, modifier = Modifier.shadow(8.dp))

            Box(Modifier.fillMaxSize()) {
                LeftNavigationColumn(Modifier.align(Alignment.TopStart))
                MiddleGrid()
                RightInfoColumn(Modifier.align(Alignment.TopEnd))
            }
        }

    }

}