import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


@Composable
fun Modifier.mouseClickableIndication(
    interactionSource: MutableInteractionSource,
    onClick: () -> Unit
): Modifier = composed {

    val rus by rememberUpdatedState(onClick)

    pointerInput(rus) {
        forEachGesture {
            coroutineScope {
                awaitPointerEventScope {
                    val down = awaitFirstDown(false)
                    val downPress = PressInteraction.Press(down.position)
                    val heldButton = launch {
                        interactionSource.emit(downPress)
                    }
                    val up = waitForUpOrCancellation()
                    heldButton.cancel()
                    val releaseOrCancel = when (up) {
                        null -> PressInteraction.Cancel(downPress)
                        else -> PressInteraction.Release(downPress)
                    }
                    launch {
                        interactionSource.emit(releaseOrCancel)
                    }
                }
            }
        }
    }.indication(interactionSource, rememberRipple(color = BasicColors.textColor))

}