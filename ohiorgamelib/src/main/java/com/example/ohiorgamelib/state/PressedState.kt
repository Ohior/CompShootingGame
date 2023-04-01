package com.example.ohiorgamelib.state

sealed class PressedState {
    object PressedDown : PressedState()
    object PressedUp : PressedState()
    object PressedMove : PressedState()
    object PressedNone : PressedState()
}
