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

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import kotlinx.android.synthetic.main.activity_one.*

/**
 * @author Dhruvaraj Nagarajan
 */
class FirstActivity : BaseActivity<FirstViewModel>() {

    override fun getLayout(): Int = R.layout.activity_one

    override fun provideViewModel(): FirstViewModel = ViewModelProviders.of(this).get(FirstViewModel::class.java)

    override fun onCreateView() {
        viewModel.lifecycle = lifecycle

        b_increment.setOnClickListener {
            viewModel.increment()
        }

        b_decrement.setOnClickListener {
            viewModel.decrement()
        }

        b_next_screen.setOnClickListener {
            val i = Intent(this, SecondActivity::class.java)
            startActivity(i)
        }
    }

    override fun onAttachObservers() {
        viewModel.countLiveData.observe(this, Observer { tv_count.text = it.toString() })
    }
}