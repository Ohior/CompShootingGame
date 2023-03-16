package com.example.compshootgame

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

object Tools {
    fun debugMessage(message: String, tag: String = "Debug-Message") {
        Log.e(tag, message)
    }

    fun getPersistentPosition(
        fromX: Int = 0,
        untilX: Int,
        fromY: Int = 0,
        untilY: Int,
        delayMillis: Long = 1000
    ): Flow<Pair<Int, Int>> {
        return flow {
            while (true) {
                delay(delayMillis)
                emit(
                    Pair(
                        Random.nextInt(fromX, untilX),
                        Random.nextInt(fromY, untilY)
                    )
                )
            }
        }
    }
}