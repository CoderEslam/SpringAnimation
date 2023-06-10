package com.doubleclick.springanimation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.Spring.StiffnessLow
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.doubleclick.springanimation.ui.theme.blue
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun SpringRelease() {
    val offsetY = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Circle(modifier = Modifier
            .offset { IntOffset(0, offsetY.value.roundToInt()) }
            .background(blue, CircleShape)
            .pointerInput(Unit) {
                forEachGesture {
                    awaitPointerEventScope {
                        //Detect a touch down event
                        awaitFirstDown()
                        do {
                            val event: PointerEvent = awaitPointerEvent()
                            event.changes.forEach { pointerInputChange: PointerInputChange ->
                                //Consume the change
                                scope.launch {
                                    offsetY.snapTo(
                                        offsetY.value + pointerInputChange.positionChange().y
                                    )
                                }
                            }
                        } while (event.changes.any { it.pressed })

                        // Touch released - Action_UP
                        scope.launch {
                            offsetY.animateTo(
                                targetValue = 0f, spring(
                                    dampingRatio = Spring.DampingRatioLowBouncy,
                                    stiffness = StiffnessLow

                                )
                            )
                        }

                    }
                }
            }
        )
    }
}

@Composable
private fun Circle(modifier: Modifier) {
    Box(
        modifier = modifier
            .size(50.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(Icons.Default.Star, contentDescription = null, tint = Color.White)
    }
}