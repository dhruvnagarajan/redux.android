package com.dhruvnagarajan.android.pubsub.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dhruvnagarajan.android.pubsub.entity.Event

/**
 * @author Dhruvaraj Nagarajan
 */
@Entity
data class EventEntity(
    @PrimaryKey
    val timestamp: Long,
    val payload: String,
    val status: Int = 200,
    val message: String? = null
)

fun Event.toEntity() =
    EventEntity(
        timestamp,
        payload,
        status,
        message
    )