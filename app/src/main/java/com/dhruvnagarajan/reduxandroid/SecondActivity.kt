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

import android.arch.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_second.*

/**
 * @author Dhruvaraj Nagarajan
 */
class SecondActivity : BaseActivity<SecondViewModel>() {

    override fun getLayout(): Int = R.layout.activity_second

    override fun provideViewModel(): SecondViewModel = ViewModelProviders.of(this).get(SecondViewModel::class.java)

    override fun onCreateView() {
        viewModel.lifecycle = lifecycle
        b_submit.setOnClickListener {
            if (et_text.text.toString().isEmpty()) {
                et_text.error = "Count cannot be empty"
                et_text.requestFocus()
                return@setOnClickListener
            }
            et_text.error = null
            viewModel.setCount(et_text.text.toString().toInt())
            finish()
        }
    }

    override fun onAttachObservers() {
    }
}