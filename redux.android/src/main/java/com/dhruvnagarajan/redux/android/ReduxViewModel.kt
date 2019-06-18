/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2019 Dhruvaraj Nagarajan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.dhruvnagarajan.redux.android

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ViewModel
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.*

/**
 * @author Dhruvaraj Nagarajan
 */
abstract class ReduxViewModel : ViewModel(), LifecycleObserver {

    var lifecycle: Lifecycle? = null
        set(value) {
            field = value
            lifecycle!!.addObserver(this)
        }

    private var d: Disposable? = null

    private val storeObserver = object : Observer<ReduxStore.SnapShot> {
        override fun onComplete() {
        }

        override fun onSubscribe(d: Disposable) {
            this@ReduxViewModel.d = d
        }

        override fun onNext(t: ReduxStore.SnapShot) {
            onStateChange(t.action, t.appState)
        }

        override fun onError(e: Throwable) {
            if (BuildConfig.DEBUG) e.printStackTrace()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun subscribeToStore() {
        ReduxStore.getInstance().subscribeWithReducers(getTag(), storeObserver, addReducers())
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun unsubscribeFromStore() {
        ReduxStore.getInstance().unSubscribe(getTag())
        d?.let { if (!it.isDisposed) it.dispose() }
    }

    /**
     * Optional override.
     * If no Reducer is provided, [ReduxStore] will consume raw payload.
     * @return reducers: list of reducers. Reducers are run in serial.
     */
    protected open fun addReducers(): List<Reducer> {
        return emptyList()
    }

    override fun onCleared() {
        lifecycle!!.removeObserver(this)
        super.onCleared()
    }

    protected fun dispatchAction(action: Action) {
        ReduxStore.getInstance().dispatchAction(action)
    }

    protected fun dispatchIncognitoAction(action: Action) {
        // you may want to use SingleEventLiveData with this
        ReduxStore.getInstance().dispatchIncognitoAction(action)
    }

    /**
     * Tag of implementing class
     */
    abstract fun getTag(): String

    /**
     * Consume redux output.
     * @param action contains the dispatched event details.
     * @param appState contains global state of the app.
     */
    abstract fun onStateChange(action: Action, appState: TreeMap<String, Any?>)
}