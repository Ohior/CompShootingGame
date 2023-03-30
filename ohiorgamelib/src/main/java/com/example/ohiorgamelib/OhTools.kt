package com.example.ohiorgamelib

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import kotlin.random.Random

object OhTools {
    @Composable
    fun OhCreateDelay(delayMillis: Long = 1000, function: () -> Unit) {
        LaunchedEffect(key1 = null, block = {
            while (true) {
                delay(delayMillis)
                function()
            }
        })
    }

    fun characterHasCollided(b: GameCharacter, r: GameCharacter): Boolean {
        return (r.xPos < (b.xPos + b.size) &&
                r.yPos < (b.yPos + b.size) &&
                (r.xPos + r.size) > b.xPos &&
                (r.yPos + r.size) > b.yPos)
    }

    fun characterHasCollided(b: GameMutableCharacter, r: GameMutableCharacter): Boolean {
        return (r.xPos < (b.xPos + b.size) &&
                r.yPos < (b.yPos + b.size) &&
                (r.xPos + r.size) > b.xPos &&
                (r.yPos + r.size) > b.yPos)
    }

    fun characterHasCollided(first: GameCharacter, second: GameMutableCharacter): Boolean {
        return (second.xPos < (first.xPos + first.size) &&
                second.yPos < (first.yPos + first.size) &&
                (second.xPos + second.size) > first.xPos &&
                (second.yPos + second.size) > first.yPos)
    }

    private fun getRandomPair(x: Int, y: Int): Pair<Int, Int> {
        return Pair(Random.nextInt(x), Random.nextInt(y))
    }

    fun getRandomPosition(start: Int, end: Int): Int {
        return Random.nextInt(start, end)
    }
}