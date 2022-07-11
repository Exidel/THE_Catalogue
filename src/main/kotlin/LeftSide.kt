import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp


@Composable
fun LeftNavigationColumn(modifier: Modifier) {

    val list = mutableListOf("11", "22", "33")
    val list2 = mutableListOf("44", "55", "66")

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
            .fillMaxHeight()
            .width(250.dp)
            .padding(10.dp)
    ) {

        LabelText(Labels().firstDDLabel + ":")

        DDMenu(list, 230.dp, RoundedCornerShape(4.dp), enableAddButton = true)

        LabelText(Labels().secondDDLabel + ":")

        DDMenu(list2, 230.dp, RoundedCornerShape(4.dp), enableAddButton = true)

        Column(Modifier.fillMaxSize().border(2.dp, BasicColors.tertiaryBGColor, RoundedCornerShape(4.dp))) {}

    }

}