import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt


@Composable
fun TopBar(
    indexLamb: (Int) -> Unit,
    tfText: String,
    tfTextLamb: (String) -> Unit,
    size: Float,
    sizeLamb: (Float) -> Unit,
    labels: Labels,
    sortIndex: (Int) -> Unit,
    mouseClickPosition: Offset
) {

    var slider by remember { mutableStateOf(size) }


    Row(
        horizontalArrangement = Arrangement.spacedBy(80.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(10.dp)
    ) {

        TextFieldWithMenu(indexLamb, tfText, tfTextLamb, labels, mouseClickPosition)

        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(labels.sort + ":", maxLines = 1, style = Styles.textStyle)

            DDMenu(labels.sortDDMenu, sortIndex, 150.dp, RoundedCornerShape(4.dp))
        }

        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(labels.size + ":", maxLines = 1, style = Styles.textStyle)

            Slider(
                value = slider,
                onValueChange = {
                    slider = it
                    sizeLamb( (( (slider).roundToInt()/64)*64).toFloat() )
                },
                steps = 1,
                modifier = Modifier.width(100.dp),
                colors = SliderDefaults.colors(
                    thumbColor = BasicColors.tertiaryBGColor,
                    activeTrackColor = Color.White,
                    inactiveTrackColor = BasicColors.primaryBGColor,
                ),
                valueRange = 128f..256f
            )
        }

    }

}