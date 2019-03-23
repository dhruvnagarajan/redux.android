package com.dhruvnagarajan.redux.android

import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

/**
 * @author Dhruvaraj Nagarajan
 */
class Store private constructor() {

    private val storeData = StoreData()

    private var storeObservable: PublishSubject<StoreData> = PublishSubject.create()

    fun subscribe(observer: Observer<StoreData>) {
        storeObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
    }

    fun putGlobal(event: Event) {
        storeData.event = event
        storeData.store[event.actionType] = event.payload
        storeObservable.onNext(storeData)
    }

    // requires optimization
    fun putLocal(event: Event) {
        val storeData = StoreData()
        storeData.event = event
        storeData.store = HashMap()
        storeData.store[event.actionType] = event.payload
        storeObservable.onNext(storeData)
    }

    data class StoreData(var event: Event = Event("init"),
                         var store: HashMap<String, Any?> = HashMap())

    internal enum class Singleton(val obj: Store) {

        INSTANCE(Store())
    }
}