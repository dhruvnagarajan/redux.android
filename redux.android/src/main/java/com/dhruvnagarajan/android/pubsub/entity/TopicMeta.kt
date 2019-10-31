package com.dhruvnagarajan.android.pubsub.entity

/**
 * @author dhruvaraj nagarajan
 */
data class TopicMeta(
    val topicName: String,
    val lastEventTime: Long,
    val eventTtl: Topic.TTL
)
