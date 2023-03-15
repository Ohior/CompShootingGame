package com.example.compshootgame

import android.util.Log

object Tools {
    fun debugMessage(message: String, tag: String = "Debug-Message") {
        Log.e(tag, message)
    }
}