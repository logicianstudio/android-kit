package com.android.kit.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.kit.viewmodel.model.UIState
import com.android.kit.viewmodel.model.UIStateException
import com.android.kit.viewmodel.model.UIStateLoading
import kotlinx.coroutines.CoroutineExceptionHandler
import java.lang.Exception

abstract class AndroidViewModelKit(application: Application) : AndroidViewModel(application) {
    private val _state = MutableLiveData<UIState>()
    val state: LiveData<UIState> get() = _state

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