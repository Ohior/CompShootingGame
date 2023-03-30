package com.example.compshootgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compshootgame.ui.theme.CompShootGameTheme
import com.example.ohiorgamelib.*


class MainActivity : ComponentActivity() {
    private val galagaCharacter = GameMutableCharacter(100f, 250f, 100f)
    private var speed = 3
    private val rocksList =
        arrayListOf(
            GameCharacter(
                OhTools.getRandomPosition(0, 400).toFloat(),
                OhTools.getRandomPosition(-500, 10).toFloat(),
                30f
            ),
            GameCharacter(
                OhTools.getRandomPosition(0, 400).toFloat(),
                OhTools.getRandomPosition(-500, 10).toFloat(),
                30f
            ),
            GameCharacter(
                OhTools.getRandomPosition(0, 400).toFloat(),
                OhTools.getRandomPosition(-500, 10).toFloat(),
                30f
            ),
            GameCharacter(
                OhTools.getRandomPosition(0, 400).toFloat(),
                OhTools.getRandomPosition(-500, 10).toFloat(),
                30f
            ),
            GameCharacter(
                OhTools.getRandomPosition(0, 400).toFloat(),
                OhTools.getRandomPosition(-500, 10).toFloat(),
                30f
            ),
            GameCharacter(
                OhTools.getRandomPosition(0, 400).toFloat(),
                OhTools.getRandomPosition(-500, 10).toFloat(),
                30f
            ),
            GameCharacter(
                OhTools.getRandomPosition(0, 400).toFloat(),
                OhTools.getRandomPosition(-500, 10).toFloat(),
                30f
            ),
            GameCharacter(
                OhTools.getRandomPosition(0, 400).toFloat(),
                OhTools.getRandomPosition(-500, 10).toFloat(),
                30f
            ),
        )
    private val bulletList = arrayListOf<GameCharacter>()

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
//                    var xPosition by remember { mutableStateOf(galagaCharacter.xPos) }
//                    var yPosition by remember { mutableStateOf(galagaCharacter.yPos) }
                    var score by remember { mutableStateOf(0) }
                    var progress by remember {
                        mutableStateOf(1f)
                    }
                    Column(Modifier.fillMaxSize()) {
                        // GAME SCREEN
                        //=====================================================================

                        if (progress <= 0.0f) {
                            Text(
                                text = "Score $score \n GAME OVER",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(0.8f)
                                    .background(
                                        Color.DarkGray
                                    )
                                    .border(5.dp, Color.Red),
                                style = TextStyle(
                                    fontFamily = FontFamily.Serif,
                                    fontWeight = FontWeight.W700,
                                    fontSize = 30.sp,
                                    color = Color.Black
                                ),
                                textAlign = TextAlign.Center,
                            )
                        } else {

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(0.8f)
                                    .border(5.dp, Color.Black),
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        modifier = Modifier.padding(20.dp),
                                        text = "Score $score",
                                        style = TextStyle(
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = FontFamily.Serif
                                        )
                                    )
                                    LinearProgressIndicator(
                                        modifier = Modifier
                                            .padding(20.dp)
                                            .clip(RoundedCornerShape(30))
                                            .size(width = 200.dp, height = 30.dp),
                                        progress = progress,
                                        color = Color(48, 92, 5, 255)
                                    )
                                }
                                if (shouldStartGameEngine()) {
                                    outer@ for (b in bulletList.indices) {
                                        // check if bullet is outside the screen
                                        if (bulletList[b].yPos < 0) {
                                            bulletList.removeAt(b)
                                            break
                                        } else {
                                            // check for collision
                                            for (r in rocksList) {
                                                if (OhTools.characterHasCollided(
                                                        bulletList[b],
                                                        r
                                                    )
                                                ) {
                                                    score++
                                                    bulletList.removeAt(b)
                                                    rocksList.remove(r)
                                                    rocksList.add(
                                                        GameCharacter(
                                                            OhTools.getRandomPosition(0, 400).toFloat(),
                                                            OhTools.getRandomPosition(0, 10).toFloat(),
                                                            30f
                                                        )
                                                    )
                                                    break@outer
                                                }

                                            }
                                        }
                                        val yPos = bulletList[b].yPos - 5
                                        bulletList[b] = GameCharacter(
                                            bulletList[b].xPos,
                                            yPos,
                                            bulletList[b].size
                                        )
                                        Box(
                                            modifier = Modifier
                                                .size(bulletList[b].size.dp)
                                                .offset(bulletList[b].xPos.dp, yPos.dp)
                                                .clip(RoundedCornerShape(100))
                                                .background(Color.Red)
                                        )
                                    }
                                    for (b in rocksList.indices) {
                                        val yPos = rocksList[b].yPos + 1
                                        // check if rock has left the screen
                                        if (rocksList[b].yPos > getHeightPercent(78f)) {
                                            rocksList[b] =
                                                GameCharacter(
                                                    OhTools.getRandomPosition(0, 400).toFloat(),
                                                    OhTools.getRandomPosition(0, 10).toFloat(),
                                                    30f
                                                )
                                        } else {
                                            rocksList[b] = GameCharacter(
                                                rocksList[b].xPos,
                                                yPos,
                                                rocksList[b].size
                                            )
                                            Box(
                                                modifier = Modifier
                                                    .size(rocksList[b].size.dp)
                                                    .offset(
                                                        rocksList[b].xPos.dp,
                                                        rocksList[b].yPos.dp
                                                    )
                                                    .clip(RoundedCornerShape(100))
                                                    .background(Color.Black)
                                            )
                                            if (OhTools.characterHasCollided(
                                                    rocksList[b],
                                                    galagaCharacter
                                                )
                                            ) {
                                                progress -= 0.1f
                                                rocksList[b] =
                                                    GameCharacter(
                                                        OhTools.getRandomPosition(0, 400).toFloat(),
                                                        OhTools.getRandomPosition(0, 10).toFloat(),
                                                        30f
                                                    )
                                            }
                                        }
                                    }
                                }
                                Image(
                                    painter = painterResource(id = R.drawable.galaga),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(galagaCharacter.size.dp)
                                        .offset(galagaCharacter.xPos.dp, galagaCharacter.yPos.dp)
                                )
                            }
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
                                upClick = { if (progress > 0.0f && galagaCharacter.yPos > 0) galagaCharacter.yPos -= speed },
                                downClick = {
                                    if (progress > 0.0f && galagaCharacter.yPos < getHeightPercent(
                                            65f
                                        )
                                    ) galagaCharacter.yPos += speed
                                },
                                leftClick = { if (progress > 0.0f && galagaCharacter.xPos > 0) galagaCharacter.xPos -= speed },
                                rightClick = { if (progress > 0.0f && galagaCharacter.xPos < MAX_WIDTH.value - galagaCharacter.size) galagaCharacter.xPos += speed })
                            OhGameButton(R.drawable.ic_baseline_back_hand_24) {
                                if (progress > 0.0f && it) {
                                    OhTools.OhCreateDelay(300) {
                                        bulletList.add(
                                            GameCharacter(
                                                galagaCharacter.xPos + 30,
                                                galagaCharacter.yPos,
                                                15f
                                            )
                                        )
                                    }
                                } else if (progress <= 0 && it) {
                                    progress = 1f
                                }
                            }
                        }
                        //=========================================================================
                    }
                }
            }
        }
    }
}

