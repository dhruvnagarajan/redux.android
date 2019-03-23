package com.dhruvnagarajan.redux.android

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * @author Dhruvaraj Nagarajan
 */
abstract class ReduxAppCompatActivity<A : Action, R : Reducer> : AppCompatActivity() {

    private lateinit var reducer: R
    protected lateinit var actions: A
    private var previousStoreState: HashMap<String, Any?> = HashMap() // clean up

    protected abstract fun provideReducer(): R

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actions = mapActions()
        actions.reduxListener = object : ReduxListener {
            override fun onAction(event: Event) {
                reducer.loopReducer(event, previousStoreState)
            }
        }
        reducer = provideReducer()
        reducer.reduxListener = object : ReduxListener {
            override fun onReduce(event: Event) {
                if (event.isLocalEvent)
                    Store.Singleton.INSTANCE.obj.putLocal(event)
                else Store.Singleton.INSTANCE.obj.putGlobal(event)
            }
        }
        subscribeToStore()
        actions.put(Event(ON_CREATE, savedInstanceState)) // diff bw resume and create
    }

    protected abstract fun mapState(store: HashMap<String, Any?>): HashMap<String, Any?>

    protected abstract fun mapActions(): A

    abstract fun render(event: Event, state: HashMap<String, Any?>)

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        pauseStoreSubscription()
        super.onPause()
    }

    override fun onStop() {
        unSubscribeFromStore()
        super.onStop()
    }

    private fun subscribeToStore() {
        Store.Singleton.INSTANCE.obj.subscribe(object : StoreChangeListener {
            override fun onChange(event: Event, store: HashMap<String, Any?>) {
                try {
                    render(event, mapState(store))
                    previousStoreState = store
                } catch (e: Exception) {
                    render(Event(ERROR_REDUX, e.message), mapState(previousStoreState))
                }
            }
        })
    }

    private fun pauseStoreSubscription() {
        unSubscribeFromStore()
    }

    private fun unSubscribeFromStore() {
    }

    companion object {
        const val ON_CREATE = "on_create"
        const val ERROR_REDUX = "error_redux"
    }
}
