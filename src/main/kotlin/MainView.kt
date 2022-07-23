import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.WindowState
import kotlinx.coroutines.coroutineScope


@Composable
fun FrameWindowScope.MainView(state: WindowState, exitApp: () -> Unit) {

/** Directory indexes */
    var libIndex by remember { mutableStateOf(0) }
    var categoryIndex by remember { mutableStateOf(0) }
    var sectionIndex by remember { mutableStateOf(0) }


/** LazyGrid variables */
    var itemSize by remember { mutableStateOf(DirManipulations.loadSettings().itemSize)}
    val mainList: MutableList<String> = DirManipulations.getMainList(libIndex, categoryIndex, sectionIndex).toMutableList()
    var selectedItem by remember { mutableStateOf("") }
    val selectedItemsList: SnapshotStateList<String> = remember { mutableStateListOf() }
    var sortIndex by remember { mutableStateOf(0) }


/** Search variables */
    var tfText by remember { mutableStateOf("") }
    var searchIndex by remember { mutableStateOf(0) }
    val searchList: MutableList<String> = DirManipulations.getSearchList(tfText, libIndex, categoryIndex, sectionIndex, searchIndex).toMutableList()
    var mouseClickPosition by remember { mutableStateOf(Offset.Zero) }


/** Localization variables */
    var langIndex by remember { mutableStateOf(DirManipulations.loadSettings().lang) }
    val labels = if (langIndex > 0) DirManipulations.loadLanguage(langIndex) else Labels()


    LaunchedEffect(libIndex, categoryIndex, sectionIndex) { selectedItem = "" }
    LaunchedEffect(tfText) { selectedItemsList.clear() }


/** UI */
    Box(
        Modifier
            .fillMaxSize()
            .background(BasicColors.secondaryBGColor)
            .pointerInput(Unit) {
                forEachGesture {
                    coroutineScope {
                        awaitPointerEventScope {
                            val down = awaitFirstDown(false)
                            mouseClickPosition = down.position
                        }
                    }
                }
            }
    ) {

        Column {

            WindowDraggableArea { DraggableArea(state, { exitApp.invoke() }, langIndex, {langIndex = it}, itemSize) }

            TopBar({searchIndex = it}, tfText, {tfText = it}, itemSize, {itemSize = it}, labels, {sortIndex = it}, mouseClickPosition)

            Divider(color = BasicColors.tertiaryBGColor, modifier = Modifier.shadow(8.dp))

            Box(Modifier.fillMaxSize()) {

                LeftNavigationColumn( libIndex, {libIndex = it}, categoryIndex, {categoryIndex = it}, sectionIndex, {sectionIndex = it}, labels )

                MiddleGrid( searchList.ifEmpty { mainList }, itemSize, selectedItemsList, {selectedItem = it}, labels, sortIndex )

                RightInfoColumn(selectedItem, mouseClickPosition)

            }

        }

    }

}