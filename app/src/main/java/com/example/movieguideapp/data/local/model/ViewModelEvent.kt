package com.example.movieguideapp.data.local.model


sealed class ViewModelEvent {
    class MessageShowing(val message: String): ViewModelEvent()
}