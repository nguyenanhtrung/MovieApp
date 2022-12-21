package com.example.movieguideapp.utils

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration

class FlowUtils {

    companion object {
        fun <T> postDelayFlow(
            data: List<T>,
            period: Duration,
            initialDelay: Duration = Duration.ZERO
        ) = flow {
            delay(initialDelay)
            var index = 0
            while (true) {
                emit(data[index])
                delay(period)
                if (index == data.size - 1) {
                    index = 0
                } else {
                    index++
                }
            }
        }
    }
}