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