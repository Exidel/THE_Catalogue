import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.WindowState
import kotlin.io.path.name
import kotlin.io.path.pathString


@Composable
fun FrameWindowScope.MainView(state: WindowState, exitApp: () -> Unit) {

/** Directory indexes */
    val dm = DirManipulations

    var libIndex by remember { mutableStateOf(0) }
    var categoryIndex by remember { mutableStateOf(0) }
    var sectionIndex by remember { mutableStateOf(0) }

    val libList = dm.getLibsList()
    val catList = dm.getCategoriesList(libIndex)
    val secList = dm.getSectionsList(libIndex, categoryIndex)

/** LazyGrid variables */
    var size by remember { mutableStateOf(64)}
    val mainList: List<String> = getMainList(libIndex, categoryIndex, sectionIndex)


/** Search variables */
    var tfText by remember { mutableStateOf("") }
    var searchIndex by remember { mutableStateOf(0) }
    val searchList: List<String> =
        if (tfText != "") {

            when(searchIndex) {

                0 -> if (dm.scanAll().isNotEmpty()) {
                    dm.scanAll().filter {
                        it.name.contains(tfText, true)
                                && it.name.contains(".jpg", true)
                                && it.name.contains("preview", true)
                    }
                        .map { it.pathString }
                } else listOf()

                1 -> if (
                    libList.isNotEmpty() && libIndex <= libList.lastIndex
                    && dm.scanSelected(dm.root + libList[libIndex]).isNotEmpty()
                ) {
                    dm.scanSelected(dm.root + libList[libIndex]).filter {
                            it.name.contains(tfText, true)
                                    && it.name.contains(".jpg", true)
                                    && it.name.contains("preview", true)
                        }.map { it.pathString }
                } else listOf()

                2 -> if (
                        libList.isNotEmpty() && libIndex <= libList.lastIndex
                        && catList.isNotEmpty() && categoryIndex <= catList.lastIndex &&
                    dm.scanSelected(dm.root + libList[libIndex] + "/" + catList[categoryIndex]).isNotEmpty()
                ) {
                    dm.scanSelected(dm.root + libList[libIndex] + "/" + catList[categoryIndex])
                        .filter { it.name.contains(tfText, true)
                                && it.name.contains(".jpg", true)
                                && it.name.contains("preview", true)
                        }
                        .map { it.pathString }
                } else listOf()

                3 -> if (
                    libList.isNotEmpty() && libIndex <= libList.lastIndex
                    && catList.isNotEmpty() && categoryIndex <= catList.lastIndex
                    && secList.isNotEmpty() && sectionIndex <= secList.lastIndex
                    && dm.scanSelected(dm.root + libList[libIndex] + "/"
                        + catList[categoryIndex] + "/"
                        + secList[sectionIndex]).isNotEmpty()
                ) {
                    dm.scanSelected(dm.root + libList[libIndex] + "/"
                            + catList[categoryIndex] + "/"
                            + secList[sectionIndex])
                        .filter { it.name.contains(tfText, true)
                                && it.name.contains(".jpg", true)
                                && it.name.contains("preview", true)
                        }
                        .map { it.pathString }
                } else listOf()

                else -> listOf()

            }

        } else listOf()



/** UI */
    Box(Modifier.fillMaxSize().background(BasicColors.secondaryBGColor)) {

        Column {

            WindowDraggableArea { DraggableArea(state) { exitApp.invoke() } }

            TopBar({ searchIndex = it }, tfText, { tfText = it }) //add size and dragPoint

            Divider(color = BasicColors.tertiaryBGColor, modifier = Modifier.shadow(8.dp))

            Box(Modifier.fillMaxSize()) {

                LeftNavigationColumn( libIndex, {libIndex = it}, categoryIndex, {categoryIndex = it}, sectionIndex, {sectionIndex = it} )

                MiddleGrid(searchList.ifEmpty { mainList }, size )

                RightInfoColumn(Modifier.align(Alignment.TopEnd))

            }

        }

    }

}



fun getMainList(libIndex: Int, categoryIndex: Int, sectionIndex: Int): List<String> {

    val dm = DirManipulations
    val libList = dm.getLibsList()
    val catList = dm.getCategoriesList(libIndex)
    val secList = dm.getSectionsList(libIndex, categoryIndex)

    val list = if (
        libList.isNotEmpty() && libIndex <= libList.lastIndex
        && catList.isNotEmpty() && categoryIndex <= catList.lastIndex
        && secList.isNotEmpty() && sectionIndex <= secList.lastIndex
        && dm.scanSelected(dm.root + libList[libIndex] + "/"
                + catList[categoryIndex] + "/"
                + secList[sectionIndex]).isNotEmpty()
    ) {
        dm.scanSelected(dm.root + libList[libIndex] + "/"
                + catList[categoryIndex] + "/"
                + secList[sectionIndex])
            .filter {
                it.name.contains("preview", true) &&
                it.name.contains(".jpg", true)
            }
            .map { it.pathString }
    } else listOf()

    return list
}