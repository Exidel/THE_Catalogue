import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MiddleGrid() {

    val scrollState = rememberLazyListState(0)

    Box(Modifier.padding(start = 250.dp, end = 250.dp).fillMaxSize().background(BasicColors.primaryBGColor).padding(10.dp)) {

//        LazyVerticalGrid(state = scrollState, cells = GridCells.Fixed(5)) {}
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp), state = scrollState) {
            items(40) { _item ->
                Text("item: $_item", Modifier.fillMaxWidth())
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