package com.dhruvnagarajan.redux.android

/**
 * State of a component.
 *
 * This will contain state variables that are relevant to your component.
 * Treat it like a local copy of the global [Store].
 * Variables that you choose to copy from the global Store shall be define in
 * mapStoreToState() method in [ReduxAppCompatActivity], [ReduxAppCompatFragment].
 *
 * @author Dhruvaraj Nagarajan
 */
data class State(var map: HashMap<String, Any?>?) {
    constructor() : this(null)
}