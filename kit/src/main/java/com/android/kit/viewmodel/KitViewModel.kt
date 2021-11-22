package com.android.kit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.kit.viewmodel.model.UiState
import com.android.kit.viewmodel.model.ExceptionUiState
import com.android.kit.viewmodel.model.LoadingUiState
import kotlinx.coroutines.*

abstract class KitViewModel : ViewModel() {
    private val _state = MutableLiveData<UiState>()
    val state: LiveData<UiState> get() = _state

    var job: Job? = null

    protected fun emitUIState(state: UiState) {
        _state.postValue(state)
    }

    protected fun emitLoading(isLoading: Boolean) {
        emitUIState(LoadingUiState(isLoading = isLoading))
    }

    protected fun emitError(exception: Exception) {
        emitLoading( isLoading = false)
        emitUIState(ExceptionUiState(exception = exception))
    }

    protected open val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        emitError(Exception(throwable))
    }

}

