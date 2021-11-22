package com.android.kit.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.kit.viewmodel.model.UiState
import com.android.kit.viewmodel.model.ExceptionUiState
import com.android.kit.viewmodel.model.LoadingUiState
import kotlinx.coroutines.CoroutineExceptionHandler
import java.lang.Exception

abstract class KitAndroidViewModel(application: Application) : AndroidViewModel(application) {
    private val _state = MutableLiveData<UiState>()
    val state: LiveData<UiState> get() = _state

    protected fun emitUIState(state: UiState) {
        _state.postValue(state)
    }

    protected fun emitLoading(isLoading: Boolean) {
        emitUIState(LoadingUiState(isLoading = isLoading))
    }

    protected fun emitError(exception: Exception) {
        emitLoading(isLoading = false)
        emitUIState(ExceptionUiState(exception = exception))
    }

    protected open val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        emitError(Exception(throwable))
    }

}