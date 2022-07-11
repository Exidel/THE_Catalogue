import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun DDMenu(
    itemsList: List<String>,
    width: Dp,
    shape: Shape,
    elevation: Dp = 8.dp,
    border: Dp = 1.dp,
    enableAddButton: Boolean = false
) {

    var expand by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf(itemsList[0]) }

    Box(
        Modifier
            .width(width)
            .wrapContentSize()
            .shadow(elevation, shape)
            .clickable { expand = !expand }
    ) {

        LabelText(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .background(BasicColors.primaryBGColor, shape)
                .border(border, BasicColors.tertiaryBGColor, shape)
                .padding(horizontal = 10.dp, vertical = 4.dp)
        )

        Icon(Icons.Default.ArrowDropDown, null, Modifier.padding(end = 5.dp).align(Alignment.CenterEnd), BasicColors.textColor)

        DropdownMenu(
            expanded = expand,
            onDismissRequest = {expand = false},
            modifier = Modifier
                .padding(vertical = 0.dp)
                .width(width)
                .background(BasicColors.primaryBGColor, RoundedCornerShape(4.dp))
                .border(1.dp, BasicColors.tertiaryBGColor, RoundedCornerShape(4.dp))
        ) {

            itemsList.forEachIndexed { _index, _item ->
                Box(
                    Modifier
                        .fillMaxWidth()
                        .clickable { text = _item; expand = false }
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    LabelText(_item)
                }

                when {
                    (itemsList.lastIndex == _index) && enableAddButton -> Divider(color = BasicColors.tertiaryBGColor)
                    itemsList.lastIndex != _index -> Divider(color = BasicColors.tertiaryBGColor)
                }

            }

            if (enableAddButton) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .clickable {  }
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    LabelText(Labels().add, Modifier.align(Alignment.Center))
                }
            }

        }

    }

}