package com.dhruvnagarajan.redux.android

interface ReduxListener {

    fun onAction(event: Event) {}

    fun onReduce(event: Event) {}
}