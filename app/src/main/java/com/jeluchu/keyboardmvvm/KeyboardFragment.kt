package com.jeluchu.keyboardmvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.jeluchu.keyboardmvvm.model.Keyboard
import com.jeluchu.keyboardmvvm.viewmodel.KeyboardVM


class KeyboardFragment : Fragment() {

    private lateinit var keyboardLayout: KeyboardLayout

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        keyboardLayout = KeyboardLayout(context, KeyboardVM.create(this))
        return keyboardLayout
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeToVM(KeyboardVM.create(this))
    }

    private fun subscribeToVM(viewModel: KeyboardVM) {
        val keyboardObserver = Observer<Keyboard> { keyboard ->
            if (keyboard != null) {
                addKeyboardToLayout(keyboard)
            }
        }
        viewModel.liveDataKeys.observe(this, keyboardObserver)
    }

    private fun addKeyboardToLayout(keyboard: Keyboard) {
        keyboardLayout.clearKeyViews()
        keyboardLayout.addKeyboard(keyboard)
    }

}