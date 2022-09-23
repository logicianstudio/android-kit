package com.android.kit.viewmodel

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.kit.viewmodel.model.UiState
import com.android.kit.viewmodel.model.ExceptionUiState
import com.android.kit.viewmodel.model.LoadingUiState
import com.android.kit.viewmodel.model.SuccessUiState
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.*
import java.lang.Exception

abstract class KitAndroidViewModel(application: Application) : AndroidViewModel(application) {

    private val handler = Handler(Looper.getMainLooper())

    private val _state = LiveEvent<UiState>()
    val state: LiveData<UiState> get() = _state

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
            }, 500)
        }
    }

    protected fun emitSuccess() {
        emitLoading(isLoading = false)
        emitUIState(SuccessUiState<Any>())
    }

    protected fun emitError(throwable: Throwable) {
        emitLoading(isLoading = false)
        (throwable as? Exception)?.let { exception ->
            emitUIState(ExceptionUiState(exception = exception))
        }
    }

    protected fun emitError(exception: Exception) {
        emitLoading(isLoading = false)
        emitUIState(ExceptionUiState(exception = exception))
    }

    protected fun emitError(message: String) {
        emitError(Exception(message))
    }

    protected open val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        emitError(throwable)
    }

    protected fun launchIO(block: suspend () -> Unit) = launch(Dispatchers.IO, block)

    protected fun launchMain(block: suspend () -> Unit) = launch(Dispatchers.Main, block)

    protected fun launch(dispatcher: CoroutineDispatcher, block: suspend () -> Unit) {
        viewModelScope.launch(dispatcher) {
            withContext(exceptionHandler) {
                block()
            }
        }
    }

}