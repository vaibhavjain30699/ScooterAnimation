package com.vaibhav.scooteranimation.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import com.vaibhav.scooteranimation.ui.viewmodel.Constants

@Composable
fun Rings(lineColor: Color, speed: Long) {

    val animatedRadiusStep by animateFloatAsState(
        targetValue = getRadiusStep(speed).toFloat(),
        animationSpec = tween(
            durationMillis = 200,
            easing = FastOutSlowInEasing
        ), label = ""
    )
    Canvas(modifier = Modifier.fillMaxSize()) {
        val strokeWidth = 4f
        val gapAngle = 25f

        val startRadius = 200f

        for (i in 1..10) {
            val currentRadius = startRadius + i * animatedRadiusStep
            // Draw four arcs for each ring
            for (startAngle in listOf(0f, 90f, 180f, 270f)) {
                drawArc(
                    color = lineColor,
                    startAngle = startAngle + gapAngle / 2,
                    sweepAngle = 90f - gapAngle,
                    useCenter = false,
                    style = Stroke(width = strokeWidth),
                    size = androidx.compose.ui.geometry.Size(
                        currentRadius * 2,
                        currentRadius * 2
                    ),
                    topLeft = Offset(
                        (size.width - currentRadius * 2) / 2,
                        (size.height - currentRadius * 2) / 2
                    )
                )
            }
        }
    }
}

private fun getRadiusStep(speed: Long) = when (speed) {
    in Constants.SPEED_1_25 -> 21
    in Constants.SPEED_26_50 -> 22
    in Constants.SPEED_51_75 -> 23
    in Constants.SPEED_75_100 -> 24
    else -> 21
}