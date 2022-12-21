package com.example.movieguideapp.extensions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun<T> LifecycleOwner.observeFlow(flow: StateFlow<T>,
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