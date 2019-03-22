package com.dhruvnagarajan.reduxandroid

import com.dhruvnagarajan.redux.android.Event
import com.dhruvnagarajan.redux.android.ReduxAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : ReduxAppCompatActivity<MainReducer, MainActions>() {

    override fun provideReducer(): MainReducer = MainReducer()

    override fun render(event: Event, state: HashMap<String, Any?>) {
        when (event.actionType) {
            ON_CREATE -> {
                setContentView(R.layout.activity_main)
                b_increment.setOnClickListener { actions.increment(1) }
                b_decrement.setOnClickListener { actions.increment(-1) }
            }
            MainActions.INCREMENT,
            MainActions.DECREMENT -> tv_count.text = "Count: " + (state["count"] as Int)
        }
    }

    override fun mapState(store: HashMap<String, Any?>): HashMap<String, Any?> {
        val state = HashMap<String, Any?>()
        store.forEach { (key, value) ->
            when (key) {
                MainActions.INCREMENT,
                MainActions.DECREMENT -> state["count"] = value
            }
        }
        return state
    }

    override fun mapActions(): MainActions = MainActions()
}
