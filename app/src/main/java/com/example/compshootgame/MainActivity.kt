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
import androidx.compose.runtime.*
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
import com.example.ohiorgamelib.button.GamePadButton
import com.example.ohiorgamelib.button.OhGameButton
import com.example.ohiorgamelib.utils.GameCharacter
import com.example.ohiorgamelib.utils.GameMutableCharacter
import com.example.ohiorgamelib.utils.OhTools


class MainActivity : ComponentActivity() {
    private val galagaCharacter = GameMutableCharacter(100f, 250f, 70f)
    private var galagaSpeed = 3
    private val bulletList = arrayListOf<GameCharacter>()
    private var score = 0
    private var progress = 1f
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
                    Column(Modifier.fillMaxSize()) {
                        // GAME SCREEN
                        //=====================================================================
                        if (progress <= 0.0f) {
                            // Game over display text
                            Text(
                                text = "Score $score \n GAME OVER",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(0.8f)
                                    .background(Color.DarkGray)
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
                            // Draw the game area
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
                                    // progressbar to indicate life
                                    LinearProgressIndicator(
                                        modifier = Modifier
                                            .padding(20.dp)
                                            .clip(RoundedCornerShape(30))
                                            .size(width = 200.dp, height = 30.dp),
                                        progress = progress,
                                        color = Color(48, 92, 5, 255)
                                    )
                                }
                                // infinity loop engine
                                if (shouldStartGameEngine()) {
                                    // Move bullets
                                    MoveBullets()
                                    // Move falling rocks
                                    MoveRocks(getHeightPercent(75f))
                                }
                                // Draw Galaga
                                Image(
                                    painter = painterResource(id = R.drawable.galaga),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(galagaCharacter.size.dp)
                                        .offset(galagaCharacter.xPos.dp, galagaCharacter.yPos.dp)
                                        .border(1.dp, Color.Blue)
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
                                upClick = { if (progress > 0.0f && galagaCharacter.yPos > 0) galagaCharacter.yPos -= galagaSpeed },
                                downClick = {
                                    if (progress > 0.0f &&
                                        galagaCharacter.yPos < getHeightPercent(70f)
                                    ) galagaCharacter.yPos += galagaSpeed
                                },
                                leftClick = { if (progress > 0.0f && galagaCharacter.xPos > 0) galagaCharacter.xPos -= galagaSpeed },
                                rightClick = { if (progress > 0.0f && galagaCharacter.xPos < MAX_WIDTH.value - galagaCharacter.size) galagaCharacter.xPos += galagaSpeed })
                            OhGameButton(R.drawable.ic_baseline_back_hand_24) {
                                if (progress > 0.0f && it) {
                                    OhTools.OhCreateDelay(300) {
                                        bulletList.add(
                                            GameCharacter(
                                                galagaCharacter.xPos + (galagaCharacter.size / 2f),
                                                galagaCharacter.yPos,
                                                15f
                                            )
                                        )
                                    }
                                }
                            }
                        }
                        //=========================================================================
                    }
                }
            }
        }
    }

    @Composable
    private fun MoveRocks(maxHeight: Float) {
        for (rock in rocksList.indices) {
            val yPos = rocksList[rock].yPos + 2
            Box(
                modifier = Modifier
                    .offset(rocksList[rock].xPos.dp, yPos.dp)
                    .size(rocksList[rock].size.dp)
                    .clip(RoundedCornerShape(100))
                    .background(Color.Black)
            )
            rocksList[rock] = GameCharacter(
                rocksList[rock].xPos, yPos, rocksList[rock].size
            )
            if (rocksList[rock].yPos > maxHeight ||
                OhTools.characterHasCollided(
                    rocksList[rock],
                    galagaCharacter
                ).apply { if (this) progress -= 0.1f }
            ) {
                rocksList[rock] =
                    GameCharacter(
                        OhTools.getRandomPosition(0, 400).toFloat(),
                        OhTools.getRandomPosition(-500, 10).toFloat(),
                        rocksList[rock].size
                    )
            }
        }
    }

    @Composable
    private fun MoveBullets() {
        bulletLoop@ for (bullet in bulletList.indices) {
            val yPos = bulletList[bullet].yPos - 5
            Box(
                modifier = Modifier
                    .offset(bulletList[bullet].xPos.dp, yPos.dp)
                    .size(bulletList[bullet].size.dp)
                    .clip(RoundedCornerShape(100))
                    .background(Color.Red)
            )
            bulletList[bullet] =
                GameCharacter(bulletList[bullet].xPos, yPos, 15f)
            if (yPos < 0f) {
                bulletList.removeAt(bullet)
                break
            }
            for (rock in rocksList) {
                if (OhTools.characterHasCollided(bulletList[bullet], rock)) {
                    score++
                    bulletList.removeAt(bullet)
                    rocksList.remove(rock)
                    break@bulletLoop
                }
            }
        }
    }
}

