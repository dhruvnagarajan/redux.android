package com.dhruvnagarajan.redux.android

/**
 * @author Dhruvaraj Nagarajan
 */
abstract class Reducer {

    lateinit var reduxListener: ReduxListener

    fun loopReducer(event: Event, store: HashMap<String, Any?>) {
        reduce(event, store)
        onComplete(event)
    }

    protected abstract fun reduce(event: Event, store: HashMap<String, Any?>)

    private fun onComplete(event: Event) {
        reduxListener.onReduce(event)
    }
}
