package com.android.kit.viewmodel.model

interface UiState

class LoadingUiState(val isLoading: Boolean, val message: String? = null) : UiState

open class SuccessUiState<T>(val data: T? = null) : UiState

class ExceptionUiState(val exception: Exception) : UiState {
    constructor(message: String) : this(Exception(message))
    val localizedMessage: String get() = exception.localizedMessage ?: "Error"
}