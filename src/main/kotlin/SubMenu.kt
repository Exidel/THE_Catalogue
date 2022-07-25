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
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import kotlin.io.path.nameWithoutExtension



@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SubMenu(
    text: String,
    expand: Boolean,
    expandLamb: (Boolean) -> Unit,
    indexLamb: (Int) -> Unit
) {

    val list = DirManipulations.getLangList()
    var menuOffset by remember { mutableStateOf(Offset.Zero) }


    Box(
        Modifier
            .onGloballyPositioned { menuOffset = it.boundsInParent().topRight }
            .pointerMoveFilter(onEnter = { expandLamb(true); false })
            .fillMaxWidth()
    ) {


        Text(text = text, maxLines = 1, style = Styles.textStyle, modifier = Modifier
            .align(Alignment.CenterStart)
            .fillMaxWidth()
            .padding(10.dp, 4.dp)
        )

        Icon(
            imageVector = if (!expand) Icons.Rounded.KeyboardArrowRight else Icons.Rounded.KeyboardArrowLeft,
            contentDescription = null,
            modifier = Modifier.size(20.dp).align(Alignment.CenterEnd).padding(end = 5.dp), BasicColors.textColor
        )


        DropdownMenu(
            expanded = expand,
            onDismissRequest = { expandLamb(false) },
            offset = DpOffset(menuOffset.x.dp, (-24).dp),
            modifier = Modifier
                .background(BasicColors.primaryBGColor, RoundedCornerShape(4.dp))
                .border(1.dp, BasicColors.tertiaryBGColor, RoundedCornerShape(4.dp))
                .pointerMoveFilter(onExit = { expandLamb(false); false })
        ) {

            if (list.isNotEmpty()) {

                list.map { it.nameWithoutExtension }.forEachIndexed() { _index, _item ->

                    Text(text = _item, maxLines = 1, style = Styles.textStyle, modifier = Modifier
                        .fillMaxWidth().clickable { indexLamb(_index) }.padding(10.dp, 4.dp)
                    )

                    if (_index != list.lastIndex) Divider(color = BasicColors.tertiaryBGColor)

                }

            }

        }

    }

}