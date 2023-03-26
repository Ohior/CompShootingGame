package com.example.ohiorgamelib

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object OhTools {
    @Composable
    fun OhCreateDelay(delayMillis: Long = 1000, function: ()->Unit){
        LaunchedEffect(key1 = null, block = {
            while (true) {
                delay(delayMillis)
                function()
            }
        })
    }
}