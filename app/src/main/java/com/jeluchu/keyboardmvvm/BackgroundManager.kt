package com.jeluchu.keyboardmvvm

import android.content.res.Resources
import androidx.core.content.res.ResourcesCompat
import com.jeluchu.keyboardmvvm.model.KeyView

class BackgroundManager (private val resources: Resources?) {
    private fun getDrawable(id: Int) = if (resources != null) ResourcesCompat.getDrawable(resources, id, null) else null
    fun setViewBackground(keyView: KeyView) {
        keyView.background = when {
            keyView.key.isWhite() && keyView.key.pulsed -> getDrawable(R.drawable.key_white_pulsed_background)
            keyView.key.isWhite() -> getDrawable(R.drawable.key_white_background)
            keyView.key.pulsed -> getDrawable(R.drawable.key_black_pulsed_background)
            else -> getDrawable(R.drawable.key_black_background)
        }
    }
}