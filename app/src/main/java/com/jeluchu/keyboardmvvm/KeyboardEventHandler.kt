package com.jeluchu.keyboardmvvm

import android.view.MotionEvent
import com.jeluchu.keyboardmvvm.model.Key
import com.jeluchu.keyboardmvvm.model.KeyView
import com.jeluchu.keyboardmvvm.viewmodel.KeyboardVM

class KeyboardEventHandler(private val keyboardVM: KeyboardVM) {

    private val keyViews = mutableListOf<KeyView>()

    fun clearKeyViews() {
        keyViews.clear()
    }

    fun setKeyViews(views: Array<KeyView>) {
        keyViews.addAll(views)
    }

    fun handleEvent(event: MotionEvent) {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN, MotionEvent.ACTION_MOVE ->
                updatePulsedKeys(event)
            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP, MotionEvent.ACTION_CANCEL ->
                releaseKey(event)
        }
    }

    private fun releaseKey(event: MotionEvent) {
        val keyView = findKey(event.getX(event.actionIndex), event.getY(event.actionIndex))
        if (keyView != null) keyboardVM.releaseKey(keyView.key)
    }

    private fun updatePulsedKeys(event: MotionEvent) {
        val keys = mutableListOf<Key>()

        val keysWithEvents = findKeysInEvents(event)
        for (view in keysWithEvents) keys.add(view.key)

        if (keys.size > 0) keyboardVM.updatePulsedKeys(keys.toTypedArray())
    }

    private fun findKey(x: Float, y: Float): KeyView? {
        val keysWithBlackFirst = keyViews.sortedBy { it.key.isWhite() }
        return keysWithBlackFirst.firstOrNull { it.left < x && it.right > x && it.top < y && it.bottom > y }
    }

    private fun findKeysInEvents(event: MotionEvent): MutableList<KeyView> {
        val keysWithEvents = mutableListOf<KeyView>()
        for (i in 0 until event.pointerCount) {
            val keyView = findKey(event.getX(i), event.getY(i))
            if (keyView != null) keysWithEvents.add(keyView)
        }
        return keysWithEvents
    }
}