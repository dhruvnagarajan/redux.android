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

import io.reactivex.Observer
import java.util.*

/**
 * @author Dhruvaraj Nagarajan
 */
class ReduxStore private constructor(
    private val appState: TreeMap<String, Any?>,
    private val isDebug: Boolean
) {

    private val observers = TreeMap<String, Observer<SnapShot>>()
    private val reducers = TreeMap<String, Reducer>()

    companion object {

        private lateinit var instance: ReduxStore

        /**
         * Instantiate using Builder().build() inside Application class.
         */
        fun getInstance(): ReduxStore {
            return instance
        }
    }

    /**
     * Without reducer.
     */
    fun subscribe(tag: String, storeObserver: Observer<SnapShot>): ReduxStore {
        observers[tag] = storeObserver
        storeObserver.onNext(SnapShot(Action(this@ReduxStore.hashCode().toString()), appState))
        return this
    }

    /**
     * You can choose to provide an implementation of [Reducer].
     * If you do, [Action] could be consumed in the reducers.
     * Otherwise, simply add [Action.payload] to the Store.
     *
     * @param tag: tag of implementing class
     * @param storeObserver: cold store observer
     * @param reducers: list of reducers. Reducers are in run serial.
     */
    fun subscribeWithReducers(
        tag: String,
        storeObserver: Observer<SnapShot>,
        reducers: List<Reducer>
    ): ReduxStore {
        observers[tag] = storeObserver
        for (reducer in reducers) {
            this.reducers[tag] = reducer
        }
        storeObserver.onNext(SnapShot(Action(this@ReduxStore.hashCode().toString()), appState))
        return this
    }

    /**
     * Single reducer.
     */
    fun subscribeWithReducer(
        tag: String,
        storeObserver: Observer<SnapShot>,
        reducer: Reducer
    ): ReduxStore {
        observers[tag] = storeObserver
        this.reducers[tag] = reducer
        storeObserver.onNext(SnapShot(Action(this@ReduxStore.hashCode().toString()), appState))
        return this
    }

    fun unSubscribe(tag: String) {
        observers.remove(tag)
        reducers.remove(tag)
    }

    /**
     * [Action.payload] is saved in the Store after going through [Reducer]s if any.
     */
    fun dispatchAction(action: Action) {
        appState[action.actionType] = action.payload
        for ((_, reducer) in reducers) {
            reducer.reduce(action, appState)
        }
        notifyObservers(Action(action.actionType, appState[action.actionType]))
    }

    /**
     * Store isn't aware of this [Action].
     * Live observers will receive this action directly, after passing through optional reducers
     */
    fun dispatchIncognitoAction(action: Action) {
        action.isIncognito = true
        val newStateForReducers = TreeMap<String, Any?>()
        newStateForReducers[action.actionType] = action.payload
        for ((_, reducer) in reducers) {
            reducer.reduce(action, newStateForReducers)
        }
        action.payload = newStateForReducers[action.actionType]
        notifyObservers(action)
    }

    private fun notifyObservers(action: Action) {
        for ((_, observer) in observers) {
            observer.onNext(SnapShot(action, appState))
        }
    }

    class Builder {

        private var appState: TreeMap<String, Any?>? = null
        private var isDebug = BuildConfig.DEBUG

        fun withInitialState(appState: TreeMap<String, Any?>): Builder {
            this.appState = appState
            return this
        }

        fun build() {
            if (appState == null) appState = TreeMap()
            instance = ReduxStore(appState!!, isDebug)
        }
    }

    data class SnapShot(val action: Action, val appState: TreeMap<String, Any?>)
}

data class Action(
    var actionType: String,
    var payload: Any? = null,
    var isIncognito: Boolean = false
)