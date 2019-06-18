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
    fun subscribeWithReducers(tag: String, storeObserver: Observer<SnapShot>, reducers: List<Reducer>): ReduxStore {
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
    fun subscribeWithReducer(tag: String, storeObserver: Observer<SnapShot>, reducer: Reducer): ReduxStore {
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
        private var isDebug = false

        fun withInitialState(appState: TreeMap<String, Any?>): Builder {
            this.appState = appState
            return this
        }

        fun isDebug(isDebug: Boolean): Builder {
            this.isDebug = isDebug
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