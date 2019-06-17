package com.jeluchu.keyboardmvvm

class Sound (val id: Int, val pitch: Int, var streamId: Int = 0) {
    fun isPlaying() = streamId != 0
}