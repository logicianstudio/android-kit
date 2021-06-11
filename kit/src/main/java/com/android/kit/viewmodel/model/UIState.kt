package com.android.kit.viewmodel.model

interface UIState

class UIStateLoading(val isLoading: Boolean) : UIState

class UIStateSuccess<T>(val data: T? = null) : UIState

class UIStateException(exception: Exception) : Exception(exception), UIState {
    constructor(message: String) : this(Exception(message))
}


