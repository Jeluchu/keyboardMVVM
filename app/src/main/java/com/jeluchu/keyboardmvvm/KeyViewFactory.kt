package com.jeluchu.keyboardmvvm

import android.content.Context
import com.jeluchu.keyboardmvvm.model.Key
import com.jeluchu.keyboardmvvm.model.KeyView

class KeyViewFactory(
        private val context: Context?,
        private val backgroundManager: BackgroundManager = BackgroundManager(context?.resources)
) {

    fun createViews(keys: Array<Key>): Array<KeyView> {
        val views = mutableListOf<KeyView>()

        for (key in keys) {
            val view = KeyView(context, key)
            backgroundManager.setViewBackground(view)
            views.add(view)
        }

        return views.toTypedArray()
    }
}