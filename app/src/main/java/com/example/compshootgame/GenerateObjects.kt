package com.example.compshootgame

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