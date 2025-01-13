package com.vaibhav.scooteranimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.vaibhav.scooteranimation.data.model.Mode
import com.vaibhav.scooteranimation.data.model.ScooterAnimationState
import com.vaibhav.scooteranimation.ui.Sizing
import com.vaibhav.scooteranimation.ui.components.BackgroundColorAnimation
import com.vaibhav.scooteranimation.ui.components.Rings
import com.vaibhav.scooteranimation.ui.theme.ScooterAnimationTheme
import com.vaibhav.scooteranimation.ui.viewmodel.Constants
import com.vaibhav.scooteranimation.ui.viewmodel.ScooterAnimationViewModel
import com.vaibhav.scooteranimation.ui.viewmodel.ScooterAnimationViewModelImpl

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: ScooterAnimationViewModelImpl by viewModels()

        setContent {
            ScooterAnimationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DashboardScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun DashboardScreen(viewModel: ScooterAnimationViewModel, modifier: Modifier = Modifier) {

    val state = viewModel.state.collectAsState()

    val animatedColor by animateColorAsState(
        targetValue = state.value.color,
        animationSpec = spring(
            dampingRatio = Constants.COLOR_ANIM_DAMPING, stiffness = Constants.COLOR_ANIM_STIFFNESS
        ), label = ""
    )

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        BackgroundColorAnimation(animatedColor)
        Rings(state.value.color, state.value.speed)
        TripDetailsContent(
            state.value,
            viewModel::onSpeedClick,
            viewModel::onModeToggleLeft,
            viewModel::onModeToggleRight
        )
    }
}

@Composable
fun TripDetailsContent(
    state: ScooterAnimationState,
    onSpeedClick: () -> Unit,
    onModeToggleLeft: () -> Unit,
    onModeToggleRight: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Sizing.spacing16),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.dashboard_title)
        )
        Text(
            text = "${state.speed}",
            modifier = Modifier.clickable {
                onSpeedClick()
            },
            style = TextStyle(
                fontSize = Sizing.font72,
                fontWeight = FontWeight.Bold
            )
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                stringResource(R.string.trip_data, state.tripA),
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f),
            )
            Modes(
                state = state,
                onModeToggleLeft = onModeToggleLeft,
                onModeToggleRight = onModeToggleRight,
            )
            Text(
                stringResource(R.string.odo_data, state.ODO),
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
        }
    }
}


@Composable
fun Modes(
    state: ScooterAnimationState,
    onModeToggleLeft: () -> Unit,
    onModeToggleRight: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = onModeToggleLeft,
        ) {
            Icon(Icons.Default.KeyboardArrowLeft, null)
        }

        ModeText(state.mode, Mode.ECONOMY, "E ")
        ModeText(state.mode, Mode.REVERSE, "R ")
        ModeText(state.mode, Mode.DYNAMIC, "D ")
        ModeText(state.mode, Mode.SONIC, "S ")

        IconButton(
            onClick = onModeToggleRight,
        ) {
            Icon(Icons.Default.KeyboardArrowRight, null)
        }
    }
}

@Composable
fun ModeText(mode: Mode, expectedMode: Mode, abbreviation: String) {
    val isSelected = (mode == expectedMode)
    Text(
        text = if (isSelected) "${expectedMode.name} " else abbreviation,
        style = if (isSelected) TextStyle(
            fontWeight = FontWeight.Bold
        ) else TextStyle()
    )
}