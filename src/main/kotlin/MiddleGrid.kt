import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.ResourceLoader
import androidx.compose.ui.res.loadImageBitmap
import org.jetbrains.skiko.loadBytesFromPath
import java.io.InputStream
import kotlin.io.path.Path
import kotlin.io.path.exists


@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun MiddleGrid() {

    val list = (1..10).map {it.toString()}
    val scrollState = rememberLazyListState(0)
    var size by remember { mutableStateOf(128.dp) }
    val path: String = "Resources\\lib2\\lib2cat3\\lib2cat3sec3\\11.jpg"


    Box(Modifier.padding(start = 250.dp, end = 250.dp).fillMaxSize().background(BasicColors.primaryBGColor).padding(10.dp)) {

        LazyVerticalGrid(
            state = scrollState,
            cells = GridCells.Adaptive(size),
            contentPadding = PaddingValues(15.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            itemsIndexed(list) { _index, _item ->

                var check by remember { mutableStateOf(false) }

                Card(elevation = 8.dp, modifier = Modifier.size(size).clickable {  }) {

                    Box(Modifier.wrapContentSize()) {

                        if (path != "" && Path(path).exists()) {
                            Image(
                                bitmap = DirManipulations.getImg(path),
                                contentDescription = null,
                                alignment = Alignment.Center,
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier.fillMaxSize()
                            )
                        } else { Box(Modifier.size(size).border(2.dp, BasicColors.tertiaryBGColor).background(BasicColors.secondaryBGColor)) }

                        Checkbox(
                            checked = check,
                            onCheckedChange = {check = it},
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