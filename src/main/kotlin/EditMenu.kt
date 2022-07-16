import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.awtEventOrNull
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.rememberCursorPositionProvider
import java.awt.event.KeyEvent



@Composable
fun EditMenu(
    switch: Boolean,
    switchLamb: (Boolean) -> Unit,
    open: () -> Unit,
    delete: () -> Unit
) {

    val transition = updateTransition(switch, "DropDownMenu")
    val transformOriginState = remember { mutableStateOf(TransformOrigin.Center) }

    val scale by transition.animateFloat(
        transitionSpec = {
            if (false isTransitioningTo true) {
                // Dismissed to expanded
                tween(
                    durationMillis = 120,
                    easing = LinearOutSlowInEasing
                )
            } else {
                // Expanded to dismissed.
                tween(
                    durationMillis = 1,
                    delayMillis = 74
                )
            }
        }
    ) {
        if (it) {
            // Menu is expanded.
            1f
        } else {
            // Menu is dismissed.
            0.8f
        }
    }

    val alpha by transition.animateFloat(
        transitionSpec = {
            if (false isTransitioningTo true) {
                // Dismissed to expanded
                tween(durationMillis = 30)
            } else {
                // Expanded to dismissed.
                tween(durationMillis = 75)
            }
        }
    ) {
        if (it) {
            // Menu is expanded.
            1f
        } else {
            // Menu is dismissed.
            0f
        }
    }

    if (switch) {

        Popup(
            onDismissRequest = { switchLamb(false) },
            focusable = true,
            popupPositionProvider = rememberCursorPositionProvider(),
            onKeyEvent = {
                if (it.awtEventOrNull?.keyCode == KeyEvent.VK_ESCAPE) {
                    switchLamb(false)
                    true
                } else {
                    false
                }
            }
        ) {
            Card(
                elevation = 8.dp,
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        this.alpha = alpha
                        transformOrigin = transformOriginState.value
                    },
            ) {
                Column( Modifier
                    .width(IntrinsicSize.Max)
                    .background(BasicColors.secondaryBGColor, RoundedCornerShape(4.dp))
                    .border(1.dp, BasicColors.tertiaryBGColor, RoundedCornerShape(4.dp))
                ) {

                    Text(
                        text = Labels().open,
                        maxLines = 1,
                        style = Styles.textStyle.copy(textAlign = TextAlign.Center),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { switchLamb(false); open.invoke() }
                            .padding(8.dp, 6.dp)
                    )

                    Divider(color = BasicColors.tertiaryBGColor)


                    Text(
                        text = Labels().delete,
                        maxLines = 1,
                        style = Styles.textStyle.copy(textAlign = TextAlign.Center),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable( onClick = { switchLamb(false); delete.invoke() })
                            .padding(8.dp, 6.dp)
                    )

                }
            }
        }

    }

}  // Right click edit menu