import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState


@Composable
fun BoxScope.MainMenu(
    enable: Boolean,
    enableLamb: (Boolean) -> Unit,
    state: WindowState,
    exit: () -> Unit
) {

    var expand by remember { mutableStateOf(false) }
    val list = Labels().menuList

    Box(Modifier.wrapContentSize().size(48.dp).align(Alignment.CenterStart).clickable { expand = !expand }) {

        Icon(
            imageVector = Icons.Rounded.Menu,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )


        DropdownMenu(
            expanded = expand,
            onDismissRequest = {expand = false},
            modifier = Modifier
                .background(BasicColors.primaryBGColor, RoundedCornerShape(4.dp))
                .border(1.dp, BasicColors.tertiaryBGColor, RoundedCornerShape(4.dp))
        ) {

            Text(text = list[0], maxLines = 1, style = Styles.textStyle, modifier = Modifier
                .fillMaxWidth().clickable { enableLamb(!enable) }.padding(10.dp, 4.dp)
            )

            Divider(color = BasicColors.tertiaryBGColor)


            Text(text = list[1], maxLines = 1, style = Styles.textStyle, modifier = Modifier
                .fillMaxWidth().clickable { state.size = DpSize(1200.dp, 800.dp) }.padding(10.dp, 4.dp)
            )

            Divider(color = BasicColors.tertiaryBGColor)


            Text(text = list[2], maxLines = 1, style = Styles.textStyle, modifier = Modifier
                .fillMaxWidth().clickable {  }.padding(10.dp, 4.dp)
            )

            Divider(color = BasicColors.tertiaryBGColor)


            Text(text = list[3], maxLines = 1, style = Styles.textStyle, modifier = Modifier
                .fillMaxWidth().clickable { exit.invoke() }.padding(10.dp, 4.dp)
            )

        }
    }

}