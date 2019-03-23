package com.dhruvnagarajan.redux.android

/**
 * @author Dhruvaraj Nagarajan
 */
class Store private constructor() {

    private var map = HashMap<String, Any?>()
    lateinit var storeChangeListener: StoreChangeListener

    fun subscribe(storeChangeListener: StoreChangeListener) {
        this.storeChangeListener = storeChangeListener
    }

    fun putGlobal(event: Event) {
        map[event.actionType] = event.payload
        storeChangeListener.onChange(event, map)
    }

    // requires optimization
    fun putLocal(event: Event) {
        val store = HashMap<String, Any?>()
        store[event.actionType] = event.payload
        storeChangeListener.onChange(event, store)
    }

    internal enum class Singleton(val obj: Store) {
        INSTANCE(Store())
    }
}

interface StoreChangeListener {
    fun onChange(event: Event, store: HashMap<String, Any?>)
}
