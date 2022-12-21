package com.example.movieguideapp.ui.base

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieguideapp.data.local.model.ViewModelEvent
import com.example.movieguideapp.data.local.model.WorkResult
import com.example.movieguideapp.domain.base.BaseUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    private val _loadingState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    internal val loadingState: StateFlow<Boolean>
        get() = _loadingState

    private val eventChannel = Channel<ViewModelEvent>(capacity = Channel.UNLIMITED)
    internal val eventFlow = eventChannel.receiveAsFlow()

    fun sendEvent(event: ViewModelEvent) {
        viewModelScope.launch {
            eventChannel.send(event)
        }
    }

    fun showloading() {
        _loadingState.value = true
    }

    fun hideLoading() {
        if (_loadingState.value) {
            _loadingState.value = false
        }
    }


    protected fun <P, R> executeUseCase(
        param: P,
        useCase: BaseUseCase<P, R>,
        isShowLoading: Boolean,
        onSuccess: (data: R) -> Unit,
        onError: (code: Int, message: String) -> Unit = { code, message -> showError(message) },
        onFailure: (throwable: Throwable) -> Unit = { throwable -> throwable.printStackTrace() }
    ) {
        viewModelScope.launch {
            try {
                if (isShowLoading) showloading()
                val result = useCase.execute(param)
                hideLoading()
                when (result) {
                    is WorkResult.Error -> onError(result.code, result.message)
                    is WorkResult.Failure -> onFailure(result.throwable)
                    is WorkResult.Success -> onSuccess(result.data)
                }
            } catch (throwable: Throwable) {
                hideLoading()
                throwable.printStackTrace()
            }
        }
    }


    protected fun showError(message: String) {
        sendEvent(ViewModelEvent.MessageShowing(message))
    }
}