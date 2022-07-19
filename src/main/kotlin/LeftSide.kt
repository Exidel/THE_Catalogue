import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.isPrimaryPressed
import androidx.compose.ui.input.pointer.isSecondaryPressed
import androidx.compose.ui.unit.dp
import kotlin.io.path.name
import kotlin.io.path.pathString


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BoxScope.LeftNavigationColumn(
    libIndex: Int,
    libLamb: (Int) -> Unit,
    categoryIndex: Int,
    catLamb: (Int) -> Unit,
    sectionIndex: Int,
    secLamb: (Int) -> Unit,
    labels: Labels
) {

    var editMenu by remember { mutableStateOf(false) }
    var deleteDialog by remember { mutableStateOf(false) }
    val dm = DirManipulations
    val libList = dm.getDirList()
    val catList = if (libIndex <= libList.lastIndex) dm.getDirList(libList[libIndex].pathString) else listOf()
    val secList = if (libIndex <= libList.lastIndex && categoryIndex <= catList.lastIndex) dm.getDirList(catList[categoryIndex].pathString) else listOf()


    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .align(Alignment.TopStart)
            .fillMaxHeight()
            .width(250.dp)
            .padding(10.dp)
    ) {

        Text(labels.firstDDLabel + ":", maxLines = 1, style = Styles.textStyle)

        DDMenu(dm.getDirList().map { it.name }, { libLamb(it) }, 230.dp, RoundedCornerShape(4.dp))

        Text(labels.secondDDLabel + ":", maxLines = 1, style = Styles.textStyle)

        DDMenu(catList.map { it.name }, { catLamb(it) }, 230.dp, RoundedCornerShape(4.dp))

        Column(
            Modifier
                .fillMaxSize()
                .background( BasicColors.primaryBGColor, RoundedCornerShape(4.dp) )
                .border( 2.dp, BasicColors.tertiaryBGColor, RoundedCornerShape(4.dp) )
        ) {

            if (secList.isNotEmpty()) {

                secList.map { it.name }.forEachIndexed { _index, _item ->

                    Text(
                        text = _item,
                        maxLines = 1,
                        style = Styles.textStyle,
                        modifier = Modifier
                            .fillMaxWidth()
                            .mouseClickable {
                                if (this.buttons.isPrimaryPressed) secLamb(_index)
                                else if (this.buttons.isSecondaryPressed) {
                                    secLamb(_index)
                                    editMenu = true
                                }
                            }
                            .padding(10.dp, 8.dp)
                    )

                    Divider(Modifier, BasicColors.tertiaryBGColor)

                }

            }

            if (editMenu) EditMenu(
                            editMenu,
                            {editMenu = it},
                            {dm.openDir(secList[sectionIndex].pathString)},
                            {deleteDialog = true}
                        )

        }

    }

    if (deleteDialog) DeleteDialog(
        isOpenLamb = {deleteDialog = it},
        labels = labels,
        message = secList[sectionIndex].pathString,
        delete = {dm.removeDir(secList[sectionIndex].pathString)} )

}