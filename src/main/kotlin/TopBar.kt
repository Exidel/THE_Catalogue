import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
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

    val list1 = listOf("lib", "sublib", "whatever")
    val list2 = listOf("date", "name", "whatever")


    Row(
        horizontalArrangement = Arrangement.spacedBy(80.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(10.dp)
    ) {

        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .shadow(8.dp, RoundedCornerShape(4.dp))
                .border(1.dp, BasicColors.tertiaryBGColor, RoundedCornerShape(4.dp))
        ) {

            TextField(170.dp)
            Divider(Modifier.fillMaxHeight().width(1.dp), BasicColors.tertiaryBGColor)
            DDMenu(list1, 120.dp, RoundedCornerShape(0.dp), Dp.Unspecified, Dp.Unspecified)

        }

        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(Labels().sort + ":", maxLines = 1, style = Styles.textStyle)

            DDMenu(list2, 150.dp, RoundedCornerShape(4.dp))
        }

        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(Labels().size + ":", maxLines = 1, style = Styles.textStyle)

            //TODO() draggable point
        }

    }

}