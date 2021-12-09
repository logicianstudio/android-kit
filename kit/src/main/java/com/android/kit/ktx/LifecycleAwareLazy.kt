package com.android.kit.ktx

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import java.io.Serializable

fun <T> LifecycleOwner.lifecycleAwareLazy(initializer: () -> T): Lazy<T> =
    LifecycleAwareLazy(this, initializer)

private object UninitializedValue

class LifecycleAwareLazy<out T>(
    private val owner: LifecycleOwner,
    initializer: () -> T
) : Lazy<T>, Serializable, LifecycleEventObserver {

    private var initializer: (() -> T)? = initializer

    private var _value: Any? = UninitializedValue

    @Suppress("UNCHECKED_CAST")
    override val value: T
        @MainThread
        get() {
            if (_value === UninitializedValue) {
                _value = initializer!!()
                attachToLifecycle()
            }

            return _value as T
        }

    private fun attachToLifecycle() {
        if (getLifecycleOwner().lifecycle.currentState == Lifecycle.State.DESTROYED) {
            throw IllegalStateException("Initialization failed because lifecycle has been destroyed!")
        }
        getLifecycleOwner().lifecycle.addObserver(this)
    }

    private fun detachFromLifecycle() {
        getLifecycleOwner().lifecycle.removeObserver(this)
    }

    private fun getLifecycleOwner() = when (owner) {
        is Fragment -> owner.viewLifecycleOwner
        else -> owner
    }

    fun resetValue() {
        _value = UninitializedValue
        detachFromLifecycle()
    }

    override fun isInitialized(): Boolean = _value !== UninitializedValue

    override fun toString(): String =
        if (isInitialized()) value.toString() else "Lazy value not initialized yet."

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            resetValue()
        }
    }

}