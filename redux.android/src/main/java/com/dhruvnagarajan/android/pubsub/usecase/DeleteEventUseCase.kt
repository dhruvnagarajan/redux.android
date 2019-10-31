package com.dhruvnagarajan.android.pubsub.usecase

import com.dhruvnagarajan.android.pubsub.entity.Topic
import com.dhruvnagarajan.android.pubsub.repository.TopicRepository

/**
 * @author Dhruvaraj Nagarajan
 */
class DeleteEventUseCase(private val topicRepository: TopicRepository) {

    fun deleteEvent() {}

    fun performHouseKeeping(topic: Topic) {}
}