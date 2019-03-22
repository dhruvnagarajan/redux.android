package com.dhruvnagarajan.reduxandroid

/**
 * @author Dhruvaraj Nagarajan
 */
fun Any?.notNull(f: () -> Unit) {
    if (this != null) f()
}