/*
 * Copyright 2019 Dhruvaraj Nagarajan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dhruvnagarajan.reduxandroid

import android.app.Application
import com.dhruvnagarajan.redux.android.BuildConfig
import com.dhruvnagarajan.redux.android.ReduxStore
import com.squareup.leakcanary.LeakCanary
import java.util.*

/**
 * @author Dhruvaraj Nagarajan
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        LeakCanary.install(this)

        val initialState = TreeMap<String, Any?>()
        initialState["count"] = 0

        ReduxStore.Builder()
            .withInitialState(initialState)
            .build()
    }
}