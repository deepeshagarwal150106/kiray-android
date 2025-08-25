package com.example.kiray.common.animation

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**Add animation in a view from different directions*/
@Composable
fun AnimatedComponent(
    direction: Direction,
    duration: Int = 1000,
    isSpring: Boolean = false,
    showAnim: Boolean = true,
    content: @Composable (Dp) -> Unit,
) {
    var showAnimation by rememberSaveable { mutableStateOf(false) }

    val offset by animateDpAsState(
        targetValue = if (showAnimation) 0.dp else direction.startOffset,
        animationSpec =
            if (isSpring) {
                spring(dampingRatio = 0.8f, stiffness = 80f)
            } else {
                tween(durationMillis = duration, easing = LinearOutSlowInEasing)
            },
        label = "move"
    )

    LaunchedEffect(Unit) {
        showAnimation = showAnim
    }

    content(offset)
}

enum class Direction(
    val startOffset: Dp,
    val modifier: @Composable (Dp) -> Modifier,
) {
    LEFT_TO_RIGHT(300.dp, { offset -> Modifier.offset(x = -offset) }),
    RIGHT_TO_LEFT(300.dp, { offset -> Modifier.offset(x = offset) }),
    TOP_TO_BOTTOM(300.dp, { offset -> Modifier.offset(y = -offset) }),
    BOTTOM_TO_TOP(300.dp, { offset -> Modifier.offset(y = offset) }),
    BOTTOM_TO_TOP_SHORT(150.dp, { offset -> Modifier.offset(y = offset) }),
}
