package com.example.kiray.ui.structure

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

fun Modifier.shimmerBackground(shape: androidx.compose.ui.graphics.Shape = RoundedCornerShape(4.dp)): Modifier = composed {
    val transition = rememberInfiniteTransition(label = "shimmerTransition")
    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f, // This value should be large enough to cover the widest item
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500), // Speed of the shimmer
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerTranslateAnimation"
    )

    val shimmerColors = listOf(
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
    )

    this.background(
        brush = Brush.linearGradient(
            colors = shimmerColors,
            start = Offset.Zero,
            end = Offset(x = translateAnimation.value, y = translateAnimation.value)
        ),
        shape = shape
    )
        .clip(shape) // Clip to the shape for better visual consistency
}

@Composable
fun ShimmerPlaceholder(
    modifier: Modifier = Modifier,
    shape: androidx.compose.ui.graphics.Shape = RoundedCornerShape(4.dp)
) {
    Box(
        modifier = modifier
            .shimmerBackground(shape)
    )
}

@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp) // Add space between shimmer items
    ) {
        // Example Skeleton Layout
        ShimmerPlaceholder(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            shape = RoundedCornerShape(8.dp) // Larger item, larger corner
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ShimmerPlaceholder(
                modifier = Modifier
                    .size(100.dp),
                shape = RoundedCornerShape(8.dp)
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ShimmerPlaceholder(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp)
                )
                ShimmerPlaceholder(
                    modifier = Modifier
                        .fillMaxWidth(0.7f) // Shorter line
                        .height(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp)) // Add some space

        repeat(3) { // Create a few list item placeholders
            ShimmerListItemPlaceholder()
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun ShimmerListItemPlaceholder() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ShimmerPlaceholder(
            modifier = Modifier
                .size(56.dp), // Placeholder for an icon or avatar
            shape = RoundedCornerShape(50) // Circular shape
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ShimmerPlaceholder(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            )
            ShimmerPlaceholder(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(20.dp)
            )
        }
    }
}

