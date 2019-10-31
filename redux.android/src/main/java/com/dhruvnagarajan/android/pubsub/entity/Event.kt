package com.dhruvnagarajan.android.pubsub.entity

/**
 * @author Dhruvaraj Nagarajan
 */
data class Event(
    val timestamp: Long,
    val payload: String,
    val status: Int = 200,
    val message: String? = null
)