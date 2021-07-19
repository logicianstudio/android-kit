package com.android.kit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.kit.listener.EventListener
import com.android.kit.viewmodel.model.UIState
import com.android.kit.viewmodel.model.UIStateException
import com.android.kit.viewmodel.model.UIStateLoading
import kotlinx.coroutines.*

abstract class ViewModelKit : ViewModel() {
    private val _state = MutableLiveData<UIState>()
    val state: LiveData<UIState> get() = _state

    var job: Job? = null

    protected fun emitUIState(state: UIState) {
        _state.postValue(state)
    }

    protected fun emitLoading(isLoading: Boolean) {
        emitUIState(UIStateLoading(isLoading = isLoading))
    }

    protected fun emitError(exception: Exception) {
        emitUIState(UIStateException(exception = exception))
    }

    protected open val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        emitError(Exception(throwable))
    }

}

