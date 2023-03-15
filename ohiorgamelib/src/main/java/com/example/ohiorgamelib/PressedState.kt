package com.example.ohiorgamelib

sealed class PressedState{
    object PressedDown : PressedState()
    object PressedUp : PressedState()
    object PressedNone : PressedState()
}
