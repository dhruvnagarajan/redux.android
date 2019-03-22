package com.dhruvnagarajan.reduxandroid

import com.dhruvnagarajan.redux.android.Action
import com.dhruvnagarajan.redux.android.Event

/**
 * @author Dhruvaraj Nagarajan
 */
class MainActions : Action() {

    fun increment(countDelta: Int) {
        put(Event(INCREMENT, countDelta))
    }

    companion object {
        const val INCREMENT = "increment"
        const val DECREMENT = "decrement"
    }
}