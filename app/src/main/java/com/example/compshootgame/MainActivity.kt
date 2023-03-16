package com.example.compshootgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.compshootgame.ui.theme.CompShootGameTheme
import com.example.ohiorgamelib.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

data class BulletPosition(val x: Dp, val y: Dp)

class MainActivity : ComponentActivity() {
    private var speed = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompShootGameTheme {
                // A surface container using the 'background' color from the theme
                OhGameSceneBox(
                    Modifier
                        .fillMaxSize()
                        .background(Color.LightGray)
                ) {
//                    var shootBullet by remember { mutableStateOf<PressedState>(PressedState.PressedNone) }
                    var shootBullet by remember { mutableStateOf(false) }
                    val numberOfRock by remember {
                        mutableStateOf(arrayListOf<Pair<Int, Int>>())
                    }
                    var rockPosition = Pair(0, 0)
                    Column(Modifier.fillMaxSize()) {
                        // GAME SCREEN
                        //=====================================================================
                        var xPosition by remember { mutableStateOf(MAX_WIDTH.value / 2) }
                        var yPosition by remember { mutableStateOf(MAX_HEIGHT.value - 300) }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.8f)
                                .border(5.dp, Color.Black)
                        ) {
                            // Persistently create and add Rocks
                            rockPosition = Tools.getPersistentPosition(
                                untilX = MAX_WIDTH.value.toInt(),
                                untilY = 10
                            ).collectAsState(
                                initial = Pair(0, 0)
                            ).value
                            numberOfRock.add(rockPosition)
                            for (i in 1 until numberOfRock.size) {
                                OhMoveComposeLinear(
                                    character = CharacterDataclass(
                                        numberOfRock[i].second.toFloat(),
                                        heightPercent(70f),
                                        1500
                                    )
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(30.dp)
                                            .offset(numberOfRock[i].first.dp, it.dp)
                                            .background(
                                                Color.Black
                                            )
                                    )
                                }
                            }
                            // Shoot Bullet
                            Image(
                                painter = painterResource(id = R.drawable.galaga),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(100.dp)
                                    .offset(xPosition.dp, yPosition.dp)
                            )

                        }
                        //========================================================================
                        // GAME PAD
                        //========================================================================
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.DarkGray),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            GamePadButton(
                                upClick = { if (yPosition > 0) yPosition -= speed },
                                downClick = { if (yPosition < heightPercent(65f)) yPosition += speed },
                                leftClick = { if (xPosition > 0) xPosition -= speed },
                                rightClick = { if (xPosition < MAX_WIDTH.value - 100) xPosition += speed })
                            OhGameButton(R.drawable.ic_baseline_back_hand_24) { pressedState ->
                                //shootBullet = pressedState
                            }
                        }
                        //=========================================================================
                    }
                }
            }
        }
    }

    private fun getRandomPosition(x: Float, y: Float): Pair<Int, Int> {
        return Pair(Random.nextInt(x.toInt()), Random.nextInt(y.toInt()))
    }

    @Composable
    private fun GamePadButton(
        upClick: () -> Unit,
        downClick: () -> Unit,
        leftClick: () -> Unit,
        rightClick: () -> Unit,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            OhGameButton(imageVector = Icons.Default.KeyboardArrowUp) {
                if (it == PressedState.PressedDown) upClick()
            }
            Row {
                OhGameButton(imageVector = Icons.Default.KeyboardArrowLeft) {
                    if (it == PressedState.PressedDown) leftClick()
                }
                Spacer(modifier = Modifier.width(50.dp))
                OhGameButton(imageVector = Icons.Default.KeyboardArrowRight) {
                    if (it == PressedState.PressedDown) rightClick()
                }
            }
            OhGameButton(imageVector = Icons.Default.KeyboardArrowDown) {
                if (it == PressedState.PressedDown) downClick()
            }
        }
    }
}

