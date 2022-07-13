import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun LeftNavigationColumn(modifier: Modifier) {

    var libIndex by remember { mutableStateOf(0) }
    var categoryIndex by remember { mutableStateOf(0) }
    var sectionIndex by remember { mutableStateOf(0) }


    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
            .fillMaxHeight()
            .width(250.dp)
            .padding(10.dp)
    ) {

        Text(Labels().firstDDLabel + ":", maxLines = 1, style = Styles.textStyle)

        DDMenu(DirManipulations.getLibsList(), { libIndex = it }, 230.dp, RoundedCornerShape(4.dp))

        Text(Labels().secondDDLabel + ":", maxLines = 1, style = Styles.textStyle)

        DDMenu(DirManipulations.getCategoriesList(libIndex), { categoryIndex = it }, 230.dp, RoundedCornerShape(4.dp))

        Column(Modifier.fillMaxSize().border(2.dp, BasicColors.tertiaryBGColor, RoundedCornerShape(4.dp))) {

            if (DirManipulations.getSectionsList(libIndex, categoryIndex).isNotEmpty()) {

                DirManipulations.getSectionsList(libIndex, categoryIndex).forEachIndexed { _index, _item ->

                    Text(
                        text = _item,
                        maxLines = 1,
                        style = Styles.textStyle,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { sectionIndex = _index }
                            .padding(10.dp, 8.dp)
                    )

                }

            }

        }

    }

}