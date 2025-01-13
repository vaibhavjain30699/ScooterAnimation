package com.vaibhav.scooteranimation.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun BackgroundColorAnimation(animatedColor: Color) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        // Define the center of the canvas
        val centerX = size.width / 2
        val centerY = size.height / 2

        // Define colors for the radial gradient with decreasing alpha
        val colors = getColorsList(animatedColor)

        // Create a radial gradient brush
        val radialBrush = Brush.radialGradient(
            colors = colors,
            center = Offset(centerX, centerY),
            radius = size.minDimension / 2,
        )

        // Draw the background using the radial brush
        drawRect(brush = radialBrush)
    }
}

private fun getColorsList(color: Color) = listOf(
    color.copy(alpha = 1f),
    color.copy(alpha = 0.75f),
    color.copy(alpha = 0.5f),
    color.copy(alpha = 0.25f),
    color.copy(alpha = 0f),
)
