import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun BoxScope.LeftNavigationColumn(
    libIndex: Int,
    libLamb: (Int) -> Unit,
    categoryIndex: Int,
    catLamb: (Int) -> Unit,
    sectionIndex: Int,
    secLamb: (Int) -> Unit
) {




    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .align(Alignment.TopStart)
            .fillMaxHeight()
            .width(250.dp)
            .padding(10.dp)
    ) {

        Text(Labels().firstDDLabel + ":", maxLines = 1, style = Styles.textStyle)

        DDMenu(DirManipulations.getLibsList(), { libLamb(it) }, 230.dp, RoundedCornerShape(4.dp))

        Text(Labels().secondDDLabel + ":", maxLines = 1, style = Styles.textStyle)

        DDMenu(DirManipulations.getCategoriesList(libIndex), { catLamb(it) }, 230.dp, RoundedCornerShape(4.dp))

        Column(
            Modifier
                .fillMaxSize()
                .background( BasicColors.primaryBGColor, RoundedCornerShape(4.dp) )
                .border( 2.dp, BasicColors.tertiaryBGColor, RoundedCornerShape(4.dp) )
        ) {

            if (DirManipulations.getSectionsList(libIndex, categoryIndex).isNotEmpty()) {

                DirManipulations.getSectionsList(libIndex, categoryIndex).forEachIndexed { _index, _item ->

                    Text(
                        text = _item,
                        maxLines = 1,
                        style = Styles.textStyle,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { secLamb(_index) }
                            .padding(10.dp, 8.dp)
                    )

                    Divider(Modifier, BasicColors.tertiaryBGColor)

                }

            }

        }

    }

}