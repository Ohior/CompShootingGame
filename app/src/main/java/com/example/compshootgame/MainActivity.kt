package com.example.compshootgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
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
import androidx.compose.ui.unit.dp
import com.example.compshootgame.ui.theme.CompShootGameTheme
import com.example.ohiorgamelib.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlin.random.Random


class MainActivity : ComponentActivity() {
    private var speed = 3
    private val rocksList =
        arrayListOf(
            GameObject(50f, 350f, 30f, 30f),
            GameObject(100f, 50f, 30f, 30f),
            GameObject(150f, 100f, 30f, 30f),
            GameObject(150f, 150f, 30f, 30f),
            GameObject(150f, 200f, 30f, 30f),
            GameObject(150f, 250f, 30f, 30f),
            GameObject(150f, 300f, 30f, 30f),
        )
    private val bulletList = arrayListOf<GameObject>()

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
                    Column(Modifier.fillMaxSize()) {
                        // GAME SCREEN
                        //=====================================================================
                        var xPosition by remember { mutableStateOf(MAX_WIDTH.value / 2) }
                        var yPosition by remember { mutableStateOf(MAX_HEIGHT.value - 300) }
                        var delayAction by remember {
                            mutableStateOf(false)
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.8f)
                                .border(5.dp, Color.Black)
                        ) {
                            var yPos by remember {
                                mutableStateOf(0)
                            }
                            // Persistently create and add Rocks
                            val transition = rememberInfiniteTransition()
                            val gameLoop by transition.animateFloat(
                                initialValue = 0f,
                                targetValue = 10f,
                                animationSpec = infiniteRepeatable(
                                    tween(
                                        1000,
                                        easing = LinearEasing
                                    ), repeatMode = RepeatMode.Restart
                                )
                            )
                            if (gameLoop != 11f) {
                                for ((i, d) in rocksList.withIndex()) {
                                    val p = Pair(d.xPos, d.yPos++)
                                    Box(
                                        modifier = Modifier
                                            .size(d.width.dp)
                                            .offset(p.first.dp, p.second.dp)
                                            .background(
                                                Color.Black
                                            )
                                    )
                                    if (p.second > 500) {
                                        val randPair = getRandomPosition(
                                            MAX_WIDTH.value,
                                            10f
                                        )
                                        rocksList.removeAt(i)
                                        rocksList.add(GameObject(randPair.first, randPair.second, 50f,50f))
                                        break
                                    }
                                }

                                bullet@for (b in bulletList) {
                                    b.yPos = b.yPos - 5
                                    Box(
                                        modifier = Modifier
                                            .size(30.dp)
                                            .offset(b.xPos.dp, b.yPos.dp)
                                            .background(
                                                Color.Black
                                            )
                                    )
                                    if (b.yPos < 10) {
                                        bulletList.remove(b)
                                        break
                                    }
                                    for (r in rocksList) {
                                        if (r.xPos < (b.xPos + b.width) &&
                                            (r.xPos + r.width) > b.xPos &&
                                            r.yPos < (b.yPos + b.height) &&
                                            (r.yPos + r.height) > b.yPos
                                        ) {
                                            val randPair = getRandomPosition(
                                                MAX_WIDTH.value,
                                                10f
                                            )
                                            rocksList.remove(r)
                                            //rocksList.add(GameObject(randPair.first, randPair.second, 50f,50f))
                                            bulletList.remove(b)
                                            break@bullet
                                        }
                                    }
                                }
//                                first@ for (i in rocksList.indices) {
//                                    for (j in bulletList.indices) {
//                                        if (bulletList.isNotEmpty() &&
//                                            rocksList[i].xPos < (bulletList[j].xPos + bulletList[j].width)
//                                            && (rocksList[i].xPos + rocksList[i].width) > bulletList[j].xPos
//                                            && rocksList[i].yPos < (bulletList[j].yPos + bulletList[j].height)
//                                            && (rocksList[i].yPos + rocksList[i].height) > bulletList[j].yPos
//                                        ) {
//                                            bulletList.removeAt(j)
//                                            rocksList.removeAt(i)
//                                            break@first
//                                        }
//                                    }
//                                }
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
                                downClick = { if (yPosition < getHeightPercent(65f)) yPosition += speed },
                                leftClick = { if (xPosition > 0) xPosition -= speed },
                                rightClick = { if (xPosition < MAX_WIDTH.value - 100) xPosition += speed })
                            OhGameButton(R.drawable.ic_baseline_back_hand_24) {
                                OhTools.OhCreateDelay(300) {
                                    bulletList.add(GameObject(xPosition, yPosition, 20f, 20f))

                                }

                            }
                        }
                        //=========================================================================
                    }
                }
            }
        }
    }

    private fun getRandomPosition(x: Float, y: Float): Pair<Float, Float> {
        return Pair(Random.nextInt(x.toInt()).toFloat(), Random.nextInt(y.toInt()).toFloat())
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

