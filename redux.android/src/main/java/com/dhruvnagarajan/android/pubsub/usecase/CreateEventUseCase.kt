package com.dhruvnagarajan.android.pubsub.usecase

import com.dhruvnagarajan.android.pubsub.entity.Event
import com.dhruvnagarajan.android.pubsub.repository.TopicRepository

/**
 * @author Dhruvaraj Nagarajan
 */
class CreateEventUseCase(private val topicRepository: TopicRepository) {

    fun createEvent(event: Event) = topicRepository.createEvent(event)
}
