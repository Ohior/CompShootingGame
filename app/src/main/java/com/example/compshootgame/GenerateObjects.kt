package com.example.compshootgame

import android.icu.text.ListFormatter.Width
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random


fun generateRandomXYCCords(): Flow<Pair<Int, Int>> {
    return flow {
        while (true){
            delay(1000)
            emit(Pair(Random.nextInt(300), Random.nextInt(0, 100)))
        }
    }
}

//data class RockData(var xPos:Float, var yPos:Float)
//
//data class PositionXY(var xPos:Float, var yPos:Float)

data class GameObject(var xPos:Float, var yPos:Float, val width: Float, val height:Float)

data class GameCharacter(var xPos:Float, var yPos:Float, val size: Float)