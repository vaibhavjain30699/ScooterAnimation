package com.vaibhav.scooteranimation.ui.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.scooteranimation.ui.viewmodel.Constants.SPEED_1_25
import com.vaibhav.scooteranimation.ui.viewmodel.Constants.SPEED_26_50
import com.vaibhav.scooteranimation.ui.viewmodel.Constants.SPEED_51_75
import com.vaibhav.scooteranimation.ui.viewmodel.Constants.SPEED_75_100
import com.vaibhav.scooteranimation.ui.viewmodel.Constants.SPEED_TIME
import com.vaibhav.scooteranimation.ui.viewmodel.Constants.TOP_SPEED
import com.vaibhav.scooteranimation.data.model.Mode
import com.vaibhav.scooteranimation.data.model.ScooterAnimationState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset

interface ScooterAnimationViewModel {
    val state: StateFlow<ScooterAnimationState>
    fun initialAnimation()
    fun onSpeedClick()
    fun onModeToggleLeft()
    fun onModeToggleRight()
}

class ScooterAnimationViewModelImpl : ViewModel(), ScooterAnimationViewModel {
    override val state = MutableStateFlow(
        ScooterAnimationState(
            speed = 0,
            ODO = 2403L,
            tripA = 123L,
        )
    )

    init {
        initialAnimation()
    }

    override fun initialAnimation() {
        viewModelScope.launch {
            val expireDate =
                LocalDateTime.now().plusSeconds(TOP_SPEED).toEpochSecond(ZoneOffset.UTC)
            val currentTime = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)

            for (i in 0..(expireDate - currentTime)) {
                state.update {
                    it.copy(
                        speed = i,
                        color = changeColor(i)
                    )
                }
                delay(SPEED_TIME)
            }

            for (i in (expireDate - currentTime) downTo 0) {
                state.update {
                    it.copy(
                        speed = i,
                        color = changeColor(i)
                    )
                }
                delay(SPEED_TIME)
            }
        }
    }

    override fun onSpeedClick() {
        initialAnimation()
    }

    override fun onModeToggleLeft() {
        if (state.value.mode.ordinal > 0) {
            state.update {
                it.copy(
                    mode = Mode.entries[it.mode.ordinal - 1]
                )
            }
            initialAnimation()
        }
    }

    override fun onModeToggleRight() {
        if (state.value.mode.ordinal < Mode.entries.size) {
            state.update {
                it.copy(
                    mode = Mode.entries[it.mode.ordinal + 1]
                )
            }
            initialAnimation()
        }
    }

    private fun changeColor(speed: Long) = when (speed) {
        in SPEED_1_25 -> Color.Green
        in SPEED_26_50 -> Color.Yellow
        in SPEED_51_75 -> Color(0xFFFFA500)
        in SPEED_75_100 -> Color.Red
        else -> Color.Green
    }

}

object Constants {
    const val TOP_SPEED = 100L
    const val SPEED_TIME = 15L

    val SPEED_1_25 = 1..25
    val SPEED_26_50 = 26..50
    val SPEED_51_75 = 51..75
    val SPEED_75_100 = 76..100

    const val COLOR_ANIM_DAMPING =0.2f
    const val COLOR_ANIM_STIFFNESS = 50f
}