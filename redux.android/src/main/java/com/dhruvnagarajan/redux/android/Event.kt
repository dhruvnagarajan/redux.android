package com.dhruvnagarajan.redux.android

/**
 * @author Dhruvaraj Nagarajan
 */
data class Event(var actionType: String, var payload: Any?) {
    constructor(actionType: String) : this(actionType, null)
}