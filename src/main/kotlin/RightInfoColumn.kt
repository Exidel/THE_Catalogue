import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun RightInfoColumn(modifier: Modifier) {

    val list = listOf("this", "is", "plug")

    LazyColumn(
        modifier
            .width(250.dp)
            .fillMaxHeight()
            .background(BasicColors.secondaryBGColor)
            .padding(10.dp)
    ) {
        itemsIndexed(list) { _index, _item ->
            Text(_item, Modifier.fillMaxHeight(), maxLines = 1, style = Styles.textStyle)
        }
    }

}