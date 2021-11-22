package com.android.kit.viewmodel.model

interface UiState

class LoadingUiState(val isLoading: Boolean) : UiState

open class SuccessUiState<T>(val data: T? = null) : UiState

class ExceptionUiState(exception: Exception) : Exception(exception), UiState {
    constructor(message: String) : this(Exception(message))
}

