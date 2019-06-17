package com.jeluchu.keyboardmvvm

import android.content.Context
import android.view.MotionEvent
import android.widget.LinearLayout
import com.jeluchu.keyboardmvvm.model.KeyView
import com.jeluchu.keyboardmvvm.model.Keyboard
import com.jeluchu.keyboardmvvm.viewmodel.KeyboardVM

class KeyboardLayout(
        context: Context?,
        keyboardVM: KeyboardVM,
        private val keyboardEventHandler: KeyboardEventHandler = KeyboardEventHandler(keyboardVM),
        private val keyViews: MutableList<KeyView> = mutableListOf(),
        private val keyViewFactory: KeyViewFactory = KeyViewFactory(context),
        private val keyboardSoundPool: KeyboardSoundPool = KeyboardSoundPool(context)
) : LinearLayout(context){

    private val relativeBlackWidth = 0.6 //relative width to white keys width
    private val relativeBlackHeight = 0.6 //relative height to white keys height
    private var keyWidth = 0

    fun clearKeyViews() {
        keyViews.clear()
        this.removeAllViews()
        this.keyboardEventHandler.clearKeyViews()
    }


    fun addKeyboard(keyboard: Keyboard) {
        val views = keyViewFactory.createViews(keyboard.keys)

        keyViews.addAll(views)
        keyboardSoundPool.updateSounds(keyboard.keys.filter { it.pulsed }.map { it.pitch }.toTypedArray())
        keyboardEventHandler.setKeyViews(views)
        for (view in views.filter { it.key.isWhite() }) this.addView(view)
        for (view in views.filter { !it.key.isWhite() }) this.addView(view)

        if (keyViews.size > 0) sizeKeys()
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) keyboardEventHandler.handleEvent(event)
        return true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (keyViews.size > 0) sizeKeys()
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if (keyViews.size > 0) sizeKeys()
    }

    private fun sizeKeys() {
        val whiteKeys = keyViews.filter { it.key.isWhite() }
        keyWidth = if (whiteKeys.isNotEmpty()) width / whiteKeys.size else 0
        var initPosition = 0

        for (view in keyViews) {
            if (view.key.isWhite()) {
                sizeWhiteKey(view, initPosition)
                initPosition++
            } else {
                sizeBlackKey(view, initPosition)
            }
        }
    }

    private fun sizeBlackKey(view: KeyView, initPosition: Int) {
        val left = ((initPosition - relativeBlackWidth / 2) * keyWidth).toInt()
        val right = (left + keyWidth * relativeBlackWidth).toInt()
        val bottom = (height * relativeBlackHeight).toInt()
        view.layout(left, 0, right, bottom)
    }

    private fun sizeWhiteKey(view: KeyView, initPosition: Int) {
        var initPosition1 = initPosition
        val right = if (view == keyViews.last()) width else (initPosition1 + 1) * keyWidth
        view.layout(initPosition1 * keyWidth, 0, right, height)
    }

}