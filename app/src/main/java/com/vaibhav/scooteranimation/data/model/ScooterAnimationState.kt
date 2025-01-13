package com.vaibhav.scooteranimation.data.model

import androidx.compose.ui.graphics.Color

data class ScooterAnimationState(
    val speed: Long = 0L,
    val color: Color = Color.Green,
    val mode: Mode = Mode.SONIC,
    val ODO: Long = 0L,
    val tripA: Long = 0L,
)

enum class Mode{
    ECONOMY,
    REVERSE,
    DYNAMIC,
    SONIC,
}