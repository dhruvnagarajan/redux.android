/*
 * Copyright 2019 Dhruvaraj Nagarajan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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