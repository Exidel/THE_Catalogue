import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.WindowState


@Composable
fun FrameWindowScope.MainView(state: WindowState, exitApp: () -> Unit) {

/** Directory indexes */
    var libIndex by remember { mutableStateOf(0) }
    var categoryIndex by remember { mutableStateOf(0) }
    var sectionIndex by remember { mutableStateOf(0) }


/** LazyGrid variables */
    var size by remember { mutableStateOf(128)}
    val mainList: List<String> = DirManipulations.getMainList(libIndex, categoryIndex, sectionIndex)
    val selectedItemList: MutableList<String> = mutableListOf()
    var selectedItem by remember { mutableStateOf("") }


/** Search variables */
    var tfText by remember { mutableStateOf("") }
    var searchIndex by remember { mutableStateOf(0) }
    val searchList: List<String> = DirManipulations.getSearchList(tfText, libIndex, categoryIndex, sectionIndex, searchIndex)




/** UI */
    Box(Modifier.fillMaxSize().background(BasicColors.secondaryBGColor)) {

        Column {

            WindowDraggableArea { DraggableArea(state) { exitApp.invoke() } }

            TopBar({searchIndex = it}, tfText, {tfText = it}, {size = it}) //add size and dragPoint

            Divider(color = BasicColors.tertiaryBGColor, modifier = Modifier.shadow(8.dp))

            Box(Modifier.fillMaxSize()) {

                LeftNavigationColumn( libIndex, {libIndex = it}, categoryIndex, {categoryIndex = it}, sectionIndex, {sectionIndex = it} )

                MiddleGrid( searchList.ifEmpty { mainList }, size, selectedItemList, {selectedItem = it} )

                RightInfoColumn(selectedItem)

            }

        }

    }

}