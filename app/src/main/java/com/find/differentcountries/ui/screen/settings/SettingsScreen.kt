package com.find.differentcountries.ui.screen.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.find.differentcountries.R
import com.find.differentcountries.ui.theme.*
import com.find.differentcountries.ui.utils.BackButton
import com.find.differentcountries.ui.utils.BackgroundImage
import com.find.differentcountries.ui.utils.innerShadow

@Composable
fun SettingsScreen(
    onBackPressed: () -> Unit,
    settingsViewModel: SettingsViewModel = hiltViewModel(),
) {

    val vibrationAmplitude by remember { settingsViewModel.vibrationAmplitude }
    val soundVolume by remember { settingsViewModel.soundVolume }

    Box(modifier = Modifier.fillMaxSize()) {

        BackgroundImage()

        Column(modifier = Modifier.fillMaxSize().padding(top = 32.dp)) {

            Box(modifier = Modifier.fillMaxWidth()) {
                BackButton(onBackPressed)
            }

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                CustomSwitch(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(), content = R.string.sound,
                    range = 0f..10f,
                    currentValue = soundVolume.toFloat()
                ) {
                    settingsViewModel.setVolume(it)
                }

                CustomSwitch(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(), content = R.string.vibration,
                    range = 0f..5f,
                    currentValue = vibrationAmplitude.toFloat()
                ) {
                    settingsViewModel.setVibrationLevel(it)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSwitch(
    modifier: Modifier = Modifier,
    content: Int,
    currentValue: Float,
    range: ClosedFloatingPointRange<Float>,
    onValueChangeFinished: (Float) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = stringResource(id = content), style = TextStyle(
                color = SettingsTextColor,
                fontFamily = FontFamily(Font(R.font.gugi)),
                fontSize = 32.sp,
                textAlign = TextAlign.Center
            ), modifier = Modifier.weight(1f)
        )

        var sliderValue by remember { mutableStateOf(0.0f) }

        ConstraintLayout(
            modifier = Modifier
                .padding(end = 56.dp, start = 4.dp, top = 28.dp, bottom = 28.dp)
                .innerShadow(offsetX = 4.dp, cornersRadius = 34.dp, blur = 4.dp)
                .background(SliderBackground, RoundedCornerShape(percent = 50))
                .padding(vertical = 36.dp)
        ) {

            val (slider, c1, c2, onOff) = createRefs()

            CircleDot(Modifier.constrainAs(c1) {
                top.linkTo(slider.top, 10.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

            CircleDot(Modifier.constrainAs(c2) {
                bottom.linkTo(slider.bottom, margin = 10.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

            OnOffState(
                modifier = Modifier.constrainAs(onOff) {
                    start.linkTo(slider.start)
                    end.linkTo(slider.end)
                    top.linkTo(slider.bottom, 6.dp)
                },
                state = currentValue != 0.0f
            )

            Slider(
                value = if (currentValue != 0.0f) currentValue else sliderValue,
                valueRange = range,
                onValueChange = {
                    sliderValue = it
                    onValueChangeFinished(sliderValue)
                },
                onValueChangeFinished = {
                },
                modifier = Modifier
                    .constrainAs(slider) {
                        top.linkTo(parent.top, 34.dp)
                        bottom.linkTo(parent.bottom, 80.dp)
                        start.linkTo(parent.start, 6.dp)
                        end.linkTo(parent.end, 6.dp)
                    }
                    .graphicsLayer {
                        rotationZ = 270f
                        transformOrigin = TransformOrigin(0f, 0f)
                    }
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(
                            Constraints(
                                minWidth = constraints.minHeight,
                                maxWidth = constraints.maxHeight,
                                minHeight = constraints.minWidth,
                                maxHeight = constraints.maxHeight,
                            )
                        )
                        layout(placeable.height, placeable.width) {
                            placeable.place(-placeable.width, 0)
                        }
                    },
                thumb = {
                    Image(
                        painter = painterResource(id = R.drawable.slider_thumb),
                        contentDescription = "slider_thumb",
                        modifier = Modifier
                            .rotate(90f)
                            .size(60.dp)
                    )
                },
                colors = SliderDefaults.colors(
                    activeTrackColor = SliderPathColor,
                    disabledInactiveTrackColor = SliderPathColor,
                    disabledActiveTickColor = SliderPathColor,
                    disabledInactiveTickColor = SliderPathColor,
                    disabledActiveTrackColor = SliderPathColor,
                    inactiveTrackColor = SliderPathColor
                )
            )
        }
    }
}

@Composable
fun CircleDot(modifier: Modifier) {
    Box(
        modifier = modifier
            .size(40.dp)
            .background(SliderCircleColor, shape = CircleShape)
    )
}

@Composable
fun OnOffState(modifier: Modifier, state: Boolean = false) {

    val color: Color = if (state) Light else Dark

    Box(
        modifier = modifier
            .size(30.dp)
            .background(color = color.copy(alpha = 0.1f), CircleShape)
            .padding(6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(18.dp)
                .background(color, CircleShape)
                .shadow(elevation = 10.dp, ambientColor = color, spotColor = color)
        )
    }
}