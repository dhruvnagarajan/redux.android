/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2019 Dhruvaraj Nagarajan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
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