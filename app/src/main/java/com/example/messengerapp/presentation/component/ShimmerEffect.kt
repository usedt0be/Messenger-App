package com.example.messengerapp.presentation.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import com.example.messengerapp.core.theme.AppTheme


fun Modifier.shimmer(): Modifier = composed {
    val shimmerColors = listOf(
        AppTheme.colors.tertiary,
        AppTheme.colors.secondary
    )
    val transition = rememberInfiniteTransition(label = "")

    val transitionAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        ),
        label = ""
    )

    this.background(
        shape = RoundedCornerShape(100),
        brush =
        Brush.linearGradient(
            colors = shimmerColors,
            start = Offset.Zero,
            end = Offset(x = transitionAnim.value, y = transitionAnim.value)
        )
    )
}