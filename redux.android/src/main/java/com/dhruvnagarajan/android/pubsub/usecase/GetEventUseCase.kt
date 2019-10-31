package com.dhruvnagarajan.android.pubsub.usecase

import com.dhruvnagarajan.android.pubsub.repository.TopicRepository
import io.reactivex.Observable

/**
 * @author Dhruvaraj Nagarajan
 */
class GetEventUseCase(private val topicRepository: TopicRepository) {

    fun <T> getAllEvents(clazz: Class<T>): Observable<List<T>> =
        Observable.create { emitter ->
            val results = topicRepository.getAllEvents(clazz)
            emitter.onNext(results)
            emitter.onComplete()
        }

    fun getEventsAfterMostRecentOccurrence(lastEventId: Long) {}
}