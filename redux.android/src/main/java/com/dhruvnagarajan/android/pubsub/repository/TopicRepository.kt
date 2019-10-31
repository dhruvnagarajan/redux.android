package com.dhruvnagarajan.android.pubsub.repository

import com.dhruvnagarajan.android.pubsub.entity.Event

/**
 * @author Dhruvaraj Nagarajan
 */
interface TopicRepository {

    fun <T> getAllEvents(clazz: Class<T>): List<T>

    fun <T> getEventsAfterMostRecentOccurrence(lastEventTimestamp: Long)

    fun <T> getEventsAfterMostRecentOccurrence(lastEvent: Event)

    fun createEvent(event: Event)

    fun deleteEvent(timestamp: Long)
}