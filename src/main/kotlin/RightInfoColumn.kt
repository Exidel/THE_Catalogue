import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
            LabelText(_item, Modifier.fillMaxHeight())
        }
    }

}