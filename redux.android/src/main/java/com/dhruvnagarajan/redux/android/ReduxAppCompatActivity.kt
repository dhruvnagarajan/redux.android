package com.dhruvnagarajan.redux.android

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * @author Dhruvaraj Nagarajan
 */
abstract class ReduxAppCompatActivity<R : Reducer, A : Action> : AppCompatActivity() {

    private lateinit var reducer: R
    lateinit var actions: A
    var state: HashMap<String, Any?>? = null
    val store = HashMap<String, Any?>()

    protected abstract fun provideReducer(): R

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actions = mapActions()
        actions.reduxListener = object : ReduxListener {
            override fun onAction(event: Event) {
                reducer.loopReducer(event, store)
            }
        }
        reducer = provideReducer()
        reducer.reduxListener = object : ReduxListener {
            override fun onReduce(event: Event) {
                render(event, mapState(store))
            }
        }
        render(Event(ON_CREATE, savedInstanceState), store)
    }

    protected abstract fun mapState(store: HashMap<String, Any?>): HashMap<String, Any?>

    protected abstract fun mapActions(): A

    abstract fun render(event: Event, state: HashMap<String, Any?>)

    override fun onResume() {
        subscribeToStore()
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

    private fun subscribeToStore() {}

    private fun pauseStoreSubscription() {}

    private fun unSubscribeFromStore() {}

    companion object {
        const val ON_CREATE = "onCreate"
    }
}
