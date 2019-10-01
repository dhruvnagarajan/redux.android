# redux.android

A global reactive Store which is an extension of `android.arch.lifecycle.ViewModel`.

## Usage

Initialize in application scope.
``` kotlin
class App : Application() {
  
    override fun onCreate() {
        super.onCreate()

        /*
         * optional initial state
         */
        val initialState = TreeMap<String, Any?>()
        initialState["count"] = 0

        ReduxStore.Builder()
            .withInitialState(initialState)
            .isDebug(BuildConfig.DEBUG)
            .build()
    }
}
```

Extend `ReduxViewModel`, to listen to `ReduxStore` directly.
``` kotlin
/**
 * ReduxViewModel : android.arch.lifecycle.ViewModel
 */
class MyViewModel : ReduxViewModel() {

    val countLiveData = MutableLiveData<Int>()

    /**
     * @return tag of implementing class
     */
    override fun getStoreTag(): String = MyViewModel::class.java.name

    /**
     * Consume redux output.
     * @param action contains the dispatched event details.
     * @param appState contains global state of the app.
     */
    override fun onStateChange(action: Action, appState: TreeMap<String, Any?>) {
        if (action.isIncognito)
            // Via dispatchIncognitoAction()
            // Store doesn't know about this action
            ...
        else
            // Via dispatchAction()
            // Store saves this action's payload
            ...
    }

    /**
     * Optional override.
     * If no Reducer is provided, [ReduxStore] will consume raw payload.
     * @return reducers: list of reducers. Reducers are run in serial.
     */
    override fun addReducers(): List<Reducer> {
        return listOf(CountReducerImpl)
    }

    // Called from view
    fun increment() {
        // Save action's payload in Store
        dispatchAction(Action("inc", 1))

        // Or,

        // Don't store action's payload in Store
        // Live observers will receive this action directly,
        // after passing through optional reducers
        dispatchIncognitoAction(Action("count", count))
    }

    // Called from view
    fun decrement() {
        dispatchAction(Action("dec", 1))
    }
}
```

Implement optional reducer. Add to `ReduxStore` using `addReducers()` in `ReduxViewModel`.
``` kotlin
/**
 * Optional implementation of Reducer.
 * Passed to Redux via [ReduxViewModel.addReducers].
 * 
 * If ReduxViewModel is not used, then reducer(s) could be passed to
 * [ReduxStore.subscribeWithReducer(s)] during subscription.
 * 
 * Reducers will be removed upon unsubscription.
 * ReduxViewModel handles subscription implicitly.
 */
object CountReducerImpl : Reducer {

    /**
     * @return reduced payload
     *
     * [Action.payload] sent via dispatch(). This payload can be consumed as is, or reduced.
     */
    override fun reduce(action: Action, appState: TreeMap<String, Any?>) {
        when (action.actionType) {
            "inc" -> {
                appState["count"] = appState["count"] as Int + action.payload as Int
            }
            "dec" -> {
                appState["count"] = appState["count"] as Int - action.payload as Int
            }
        }
    }
}
```

# Design philosophies behind **redux.android**

This library is simply more than vanilla redux.

- `ReduxStore` could be used directly from anywhere, but `ReduxViewModel` should be the preferred entry point
- Manage local app state along with the global one, in `ViewModel` itself
- Includes `incognito` mode to keep the Store blind, to support pub/sub architecture

# License
```
Apache License 2.0

Copyright 2019 Dhruvaraj Nagarajan

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```