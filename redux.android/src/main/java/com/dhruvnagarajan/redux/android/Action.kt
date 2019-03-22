package com.dhruvnagarajan.redux.android

/**
 * @author Dhruvaraj Nagarajan
 */
abstract class Action {

    lateinit var reduxListener: ReduxListener

    fun put(event: Event) {
        reduxListener.onAction(event)
    }
}