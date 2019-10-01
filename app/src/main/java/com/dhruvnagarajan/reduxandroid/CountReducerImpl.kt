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