package com.dhruvnagarajan.reduxandroid

import com.dhruvnagarajan.redux.android.Event
import com.dhruvnagarajan.redux.android.Reducer

/**
 * @author Dhruvaraj Nagarajan
 */
class MainReducer : Reducer() {

    override fun reduce(event: Event, store: HashMap<String, Any?>) {
        when (event.actionType) {
            MainActions.INCREMENT -> {
                var previousValue: Int? = store["count"] as? Int
                if (previousValue == null)
                    previousValue = 0
                store["count"] = previousValue + event.payload as Int
            }
        }
    }
}