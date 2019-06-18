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

import com.dhruvnagarajan.redux.android.Action
import com.dhruvnagarajan.redux.android.Reducer
import java.util.*

/**
 * @author Dhruvaraj Nagarajan
 *
 * Optional implementation of Reducer.
 * Passed to Redux via [ReduxViewModel.addReducers].
 *
 * If ReduxViewModel is not used, then reducer(s) could be passed to
 * [ReduxStore.subscribeWithReducer(s)] during subscription.
 *
 * Reducers will be removed upon unsubscription.
 * ReduxViewModel handles subscription implicitly.
 */
object CountReducerImpl : Reducer {

    /**
     * @return reduced payload
     *
     * [Action.payload] sent via dispatch(). This payload can be consumed as is, or reduced.
     */
    override fun reduce(action: Action, appState: TreeMap<String, Any?>) {
        when (action.actionType) {
            "inc" -> {
                appState["count"] = appState["count"] as Int + action.payload as Int
            }
            "dec" -> {
                appState["count"] = appState["count"] as Int - action.payload as Int
            }
        }
    }
}