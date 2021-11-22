package com.android.kit.viewmodel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.kit.viewmodel.model.UiState
import com.android.kit.viewmodel.model.ExceptionUiState
import com.android.kit.viewmodel.model.LoadingUiState
import kotlinx.coroutines.*

abstract class KitViewModel : ViewModel() {

    private val handler = Handler(Looper.getMainLooper())
    private val _state = MutableLiveData<UiState>()
    val state: LiveData<UiState> get() = _state

    var job: Job? = null

    protected fun emitUIState(state: UiState) {
        _state.postValue(state)
    }

    protected fun emitLoading(isLoading: Boolean) {
        if (isLoading) {
            handler.removeCallbacksAndMessages(null)
            emitUIState(LoadingUiState(isLoading = isLoading))
        } else {
            handler.postDelayed({
                emitUIState(LoadingUiState(isLoading = isLoading))
            }, 700)
        }
    }

    protected fun emitError(exception: Exception) {
        emitLoading(isLoading = false)
        emitUIState(ExceptionUiState(exception = exception))
    }

    protected fun emitError(message: String) {
        emitError(ExceptionUiState(message = message))
    }

    protected open val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        emitError(Exception(throwable.message))
    }

}

