package com.doubleclick.springanimation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.Spring.StiffnessVeryLow
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import androidx.compose.ui.unit.toOffset
import com.doubleclick.springanimation.ui.theme.Purple500
import com.doubleclick.springanimation.ui.theme.Purple700
import com.doubleclick.springanimation.ui.theme.Teal200
import kotlinx.coroutines.launch

@Composable
fun SpringAnimation() {

    val scope = rememberCoroutineScope()

    val offset = remember { Animatable(Offset(0f, 0f), Offset.VectorConverter) }

    val offset2 = remember { Animatable(Offset(0f, 0f), Offset.VectorConverter) }

    val offset3 = remember { Animatable(Offset(0f, 0f), Offset.VectorConverter) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Circle(modifier = Modifier
            .offset {
                offset.value.round()
            }
            .background(Teal200, CircleShape)
            .pointerInput(Unit) {

                detectDragGestures { change, dragAmount ->
                    change.consumeAllChanges()
                    scope.launch {
                        offset.animateTo(
                            offset.value + change.position, spring(
                                dampingRatio = Spring.DampingRatioLowBouncy
                            )
                        )
                    }
                    scope.launch {
                        offset2.animateTo(
                            offset.value + change.position, spring(
                                dampingRatio = Spring.DampingRatioLowBouncy,
                                stiffness = 100f
                            )
                        )
                    }

                    scope.launch {
                        offset3.animateTo(
                            offset.value + change.position, spring(
                                dampingRatio = Spring.DampingRatioLowBouncy,
                                stiffness = StiffnessVeryLow
                            )
                        )
                    }
                }

            })

        Spacer(modifier = Modifier.height(30.dp))
        
        Circle(modifier = Modifier
            .offset {
                offset2.value.round()
            }
            .background(Purple500.copy(0.8f), CircleShape))


        Spacer(modifier = Modifier.height(30.dp))
        Circle(modifier = Modifier
            .offset {
                offset3.value.round()
            }
            .background(Purple700.copy(0.3f), CircleShape))
    }
}


@Composable
fun Circle(modifier: Modifier) {
    Box(
        modifier = modifier
            .size(50.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(Icons.Default.Star, contentDescription = null, tint = Color.White)
    }
}