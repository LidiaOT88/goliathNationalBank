package com.example.goliathnationalbank.data.lifecycle

import androidx.lifecycle.Observer

abstract class EventAsyncResultObserver<T> : Observer<Event<AsyncResult<T>>> {

    override fun onChanged(event: Event<AsyncResult<T>>?) {
        event?.getContentIfNotHandled()?.let {
            when {
                it.isSuccess() -> {
                    onLoading(false)
                    onSuccess(it.data)
                }
                it.isError() -> {
                    val error = (it as AsyncResult.Error).error
                    onLoading(false)
                    onError(error, it.data)
                }
                else -> {
                    onLoading(true)
                }
            }
        }
    }

    abstract fun onSuccess(response: T?)

    open fun onError(error: String, response: T?) {
        // no-op
    }

    open fun onLoading(loading: Boolean) {
        // no-op
    }

}