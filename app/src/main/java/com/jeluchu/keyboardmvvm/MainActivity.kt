package com.jeluchu.keyboardmvvm

import androidx.appcompat.app.AppCompatActivity

import android.content.pm.ActivityInfo
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

    }
}
