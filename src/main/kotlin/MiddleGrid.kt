import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import kotlin.io.path.*


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MiddleGrid(
    list: MutableList<String>,
    size: Float,
    selectedItemsList: SnapshotStateList<String>,
    selectedItem: (String) -> Unit,
    labels: Labels,
    sortIndex: Int
) {

    val scrollState = rememberLazyListState(0)
    var deleteIcon by mutableStateOf(selectedItemsList.isNotEmpty())
    var deleteDialog by remember { mutableStateOf(false) }


    Column(Modifier.padding(start = 250.dp, end = 250.dp).fillMaxSize().background(BasicColors.primaryBGColor).padding(10.dp)) {

        AnimatedVisibility(
            visible = deleteIcon,
            modifier = Modifier.align(Alignment.CenterHorizontally).clip(CircleShape)
        ) {
            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clickable { if (selectedItemsList.isNotEmpty()) deleteDialog = true }
                    .padding(5.dp),
                tint = Color.White)
        }


        Box {

            LazyVerticalGrid(
                state = scrollState,
                cells = GridCells.Adaptive(size.dp),
                contentPadding = PaddingValues(15.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                if (list.isNotEmpty()) {
                    items(
                        when(sortIndex) {
                            0 -> list
                            1 -> list.sortedBy { Path(it).nameWithoutExtension.lowercase() }
                            2 -> list.sortedByDescending { Path(it).nameWithoutExtension.lowercase() }
                            else -> list
                        }
                    ) { _item ->

                        var check by remember { mutableStateOf(false) }
                        if (selectedItemsList.isEmpty()) check = false

                        TooltipArea(
                            tooltip = { Text(_item, style = Styles.textStyle, modifier = Modifier
                                .background(BasicColors.primaryBGColor, RoundedCornerShape(4.dp))
                                .border(1.dp, BasicColors.tertiaryBGColor, RoundedCornerShape(4.dp))
                                .padding(10.dp, 5.dp)
                            ) }
                        ) {
                            Card(elevation = 8.dp,
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .size(size.dp)
                                    .combinedClickable(
                                        onClick = { selectedItem(_item) },
                                        onDoubleClick = {
                                            selectedItem(_item)
                                            DirManipulations.openDir( Path(_item).parent.pathString )
                                        }
                                    )
                            ) {

                                Box(Modifier.fillMaxSize()) {

                                    if (_item != "" && Path(_item).exists()) {
                                        Image(
                                            bitmap = DirManipulations.getImg(_item),
                                            contentDescription = null,
                                            alignment = Alignment.Center,
                                            contentScale = ContentScale.FillBounds,
                                            modifier = Modifier.fillMaxSize()
                                        )
                                    } else { Box(Modifier.size(size.dp).border(2.dp, BasicColors.tertiaryBGColor).background(BasicColors.secondaryBGColor)) }

                                    Checkbox(
                                        checked = check,
                                        onCheckedChange = {
                                            check = it

                                            if (check && !selectedItemsList.contains(_item)) {
                                                selectedItemsList.add(_item)
                                            } else if (!check && selectedItemsList.contains(_item))  {
                                                selectedItemsList.remove(_item)
                                            }

                                            deleteIcon = selectedItemsList.isNotEmpty()
                                        },
                                        modifier = Modifier.align(Alignment.TopStart),
                                        colors = CheckboxDefaults.colors(
                                            checkedColor = BasicColors.primaryBGColor,
                                            uncheckedColor = BasicColors.tertiaryBGColor
                                        )
                                    )

                                }

                            }
                        }

                    }

                }

            }


            if (list.isNotEmpty()) {
                VerticalScrollbar(
                    adapter = ScrollbarAdapter(scrollState),
                    modifier = Modifier.fillMaxHeight().align(Alignment.CenterEnd),
                    style = defaultScrollbarStyle().copy(
                        unhoverColor = BasicColors.secondaryBGColor.copy(alpha = 0.5f),
                        hoverColor = BasicColors.tertiaryBGColor.copy(alpha = 1f),
                        hoverDurationMillis = 200
                    )
                )
            }

        }

    }


    if (deleteDialog) {
        val messages: MutableSet<String> = mutableSetOf()
        selectedItemsList.forEach { messages.add(Path(it).parent.pathString) }
        DeleteDialog(
            isOpenLamb = {deleteDialog = it},
            labels = labels,
            message = messages.toList(),
            delete = {
                if (selectedItemsList.isNotEmpty()) {
                    selectedItemsList.forEach { DirManipulations.removeDir(Path(it).parent.pathString) }
                    list.removeAll(selectedItemsList)
                    selectedItemsList.clear()
                }
            }
        )
    }


}