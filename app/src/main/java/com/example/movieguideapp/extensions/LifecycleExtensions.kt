package com.example.movieguideapp.extensions

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun<T> LifecycleOwner.observeFlow(flow: StateFlow<T>,
                                  viewLifecycleOwner: LifecycleOwner? = null,
                                  lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
                                  onCollected: (data: T) -> Unit) {
    if (viewLifecycleOwner != null) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(lifecycleState) {
                flow.collect {
                    onCollected(it)
                }
            }
        }
    } else {
        lifecycleScope.launch {
            repeatOnLifecycle(lifecycleState) {
                flow.collect {
                    onCollected(it)
                }
            }
        }
    }
}

fun<T> LifecycleOwner.observeFlow(flow: Flow<T>,
                                  lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
                                  onCollected: (data: T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(lifecycleState) {
            flow.collect {
                onCollected(it)
            }
        }
    }
}

fun Fragment.postDelay(initialDelay: Long, period: Long, codeBlock: () -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        delay(initialDelay)
        while (true) {
            delay(period)
            codeBlock()
        }
    }
}