package com.example.movieguideapp.data.local.model

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class SingleLiveEvent<T> {
    private val eventChannel = Channel<T>(capacity = Channel.UNLIMITED)
    val eventFlow = eventChannel.receiveAsFlow()

    suspend fun send(event: T) {
        eventChannel.send(event)
    }
}