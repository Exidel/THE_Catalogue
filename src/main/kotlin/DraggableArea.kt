import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun DraggableArea(onClick: () -> Unit) {

    Column {
        Box(Modifier.fillMaxWidth().height(48.dp).background(BasicColors.secondaryBGColor).padding(horizontal = 10.dp)) {
            Icon(Icons.Default.Menu, null, tint = Color.White, modifier = Modifier.align(Alignment.CenterStart))

            Icon(Icons.Default.AccountBox, null, tint = Color.White, modifier = Modifier.align(Alignment.Center))

            Row(Modifier.align(Alignment.CenterEnd), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Icon(Icons.Default.Close, null, tint = Color.White, modifier = Modifier.clickable { onClick.invoke() })
            }
        }

        Divider(Modifier.shadow(8.dp), BasicColors.tertiaryBGColor, 1.dp)
    }

}