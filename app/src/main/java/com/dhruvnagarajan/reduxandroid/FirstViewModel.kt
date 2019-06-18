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

package com.dhruvnagarajan.reduxandroid

import android.arch.lifecycle.MutableLiveData
import com.dhruvnagarajan.redux.android.Action
import com.dhruvnagarajan.redux.android.Reducer
import com.dhruvnagarajan.redux.android.ReduxViewModel
import java.util.*

/**
 * @author Dhruvaraj Nagarajan
 */
class FirstViewModel : ReduxViewModel() {

    val countLiveData = MutableLiveData<Int>()

    override fun getTag(): String = FirstViewModel::class.java.name

    override fun onStateChange(action: Action, appState: TreeMap<String, Any?>) {
        if (action.isIncognito && action.actionType == "count") countLiveData.postValue(action.payload as Int)
        else countLiveData.postValue(appState["count"] as Int)
    }

    /**
     * Optional override.
     * If an action is not reduced in any reducer, eg: [CountReducerImpl], then [ReduxStore.dispatchAction] will add unreduced payload to Store.
     *
     * @return reducers: list of reducers.
     * An action is passed through all of them for reduction.
     * Reducers are in run serial.
     */
    override fun addReducers(): List<Reducer> {
        return listOf(CountReducerImpl)
    }

    fun increment() {
        // Store action's payload in Store; reduced, if reducer is provided.
        // Otherwise original payload will be sent.
        dispatchAction(Action("inc", 1))

        // Or,

        // Don't store action's payload in Store.
        // Observers currently subscribed to Store will receive this action directly.
        // Reduced if reducer is provided, otherwise original payload will be sent.
//        dispatchIncognitoAction(Action("inc", 1))
    }

    fun decrement() {
        dispatchAction(Action("dec", 1))
    }
}