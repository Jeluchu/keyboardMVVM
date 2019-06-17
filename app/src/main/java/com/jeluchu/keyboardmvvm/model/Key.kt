package com.jeluchu.keyboardmvvm.model

class Key (val pitch: Int, var pulsed: Boolean = false) {
    fun isWhite() : Boolean = pitch in arrayListOf(0, 2, 4, 5, 7, 9, 11)
}