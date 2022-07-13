import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun TopBar() {

    val list2 = listOf("date", "name", "whatever")
    var indexPlug by remember { mutableStateOf(0) }


    Row(
        horizontalArrangement = Arrangement.spacedBy(80.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(10.dp)
    ) {

        TextFieldWithMenu()

        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(Labels().sort + ":", maxLines = 1, style = Styles.textStyle)

            DDMenu(list2, {indexPlug = it}, 150.dp, RoundedCornerShape(4.dp))
        }

        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(Labels().size + ":", maxLines = 1, style = Styles.textStyle)

            Icon(Icons.Default.Search, null, tint = Color.White)
        }

    }

}