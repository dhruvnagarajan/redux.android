package com.dhruvnagarajan.android.pubsub.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dhruvnagarajan.android.pubsub.entity.Topic
import com.dhruvnagarajan.android.pubsub.entity.TopicMeta

/**
 * @author dhruvaraj nagarajan
 */
@Entity
data class TopicMetaEntity(
    @PrimaryKey
    val topicName: String,
    val lastEventTime: Long,
    val eventTtlType: String,
    val eventTtlValue: Int
)

fun TopicMetaEntity.toPojo() =
    TopicMeta(
        topicName,
        lastEventTime,
        toTTL(eventTtlType, eventTtlValue)
    )

fun TopicMeta.toEntity() =
    TopicMetaEntity(
        topicName,
        lastEventTime,
        fromTTL(eventTtl).first,
        fromTTL(eventTtl).second
    )

private fun toTTL(ttl: String, value: Int) = when (ttl) {
    "DAY" -> Topic.TTL.DAY(value)
    "HOUR" -> Topic.TTL.HOUR(value)
    "MINUTE" -> Topic.TTL.MINUTE(value)
    "MILLIS" -> Topic.TTL.MILLIS(value)
    "REMOVE_ON_CONSUMPTION" -> Topic.TTL.REMOVE_ON_CONSUMPTION
    "NOT_PERSISTED" -> Topic.TTL.NOT_PERSISTED
    else -> Topic.TTL.NOT_PERSISTED
}

private fun fromTTL(ttl: Topic.TTL) = Pair(
    when (ttl) {
        is Topic.TTL.DAY -> "DAY"
        is Topic.TTL.HOUR -> "HOUR"
        is Topic.TTL.MINUTE -> "MINUTE"
        is Topic.TTL.MILLIS -> "MILLIS"
        Topic.TTL.REMOVE_ON_CONSUMPTION -> "REMOVE_ON_CONSUMPTION"
        Topic.TTL.NOT_PERSISTED -> "NOT_PERSISTED"
    }, ttl.ttl
)