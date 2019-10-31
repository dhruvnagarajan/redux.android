package com.dhruvnagarajan.android.pubsub.entity

import android.content.Context
import com.dhruvnagarajan.android.pubsub.entity.Topic.TTL
import com.dhruvnagarajan.android.pubsub.repository.TopicRepositoryImpl
import com.dhruvnagarajan.android.pubsub.usecase.CreateEventUseCase
import com.dhruvnagarajan.android.pubsub.usecase.GetEventUseCase
import com.dhruvnagarajan.android.pubsub.util.toJson
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject
import io.reactivex.subjects.Subject
import java.lang.Exception

/**
 * @param ttl default is [TTL.REMOVE_ON_CONSUMPTION], set by [PubSub.createTopic]
 *
 * @author Dhruvaraj Nagarajan
 */
class Topic(
    val topicKey: String,
    private var ttl: TTL,
    val context: Context
) {

    // todo remove
    val topicRepository = TopicRepositoryImpl(context)
    val createEventUseCase = CreateEventUseCase(topicRepository)
    val getEventUseCase: GetEventUseCase = GetEventUseCase(topicRepository)

    val bridge: Subject<Any> = when (ttl) {
        is TTL.DAY -> ReplaySubject.create()
        is TTL.HOUR -> ReplaySubject.create()
        is TTL.MINUTE -> ReplaySubject.create()
        is TTL.MILLIS -> ReplaySubject.create()
        is TTL.REMOVE_ON_CONSUMPTION -> PublishSubject.create()
        is TTL.NOT_PERSISTED -> PublishSubject.create()
    }

    init {
        bridge
            .observeOn(Schedulers.io())
            .subscribe {
                try {
                    createEventUseCase.createEvent(
                        Event(
                            System.currentTimeMillis(),
                            payload = it.toJson()
                        )
                    )
                } catch (e:Exception) {
                    // todo log to meta
                }
            }
    }

    /**
     * set new TTL and remove events from storage that no longer conform to the newly desired TTL.
     */
    fun setTTL(ttl: TTL) {
        this.ttl = ttl
//        deleteEventUseCase.performHouseKeeping(this)
    }

    /**
     * Time To Live.
     *
     * topic records are persisted using room.
     * records have to be deleted after a timeout.
     * choose your desired timeout for persistence in this topic from below.
     */
    sealed class TTL(val ttl: Int) {
        data class DAY(val value: Int) : TTL(1000 * 60 * 60 * 24 * value)
        data class HOUR(val value: Int) : TTL(1000 * 60 * 60 * value)
        data class MINUTE(val value: Int) : TTL(1000 * 60 * value)
        data class MILLIS(val value: Int = 1000) : TTL(value)
        object REMOVE_ON_CONSUMPTION : TTL(0)
        object NOT_PERSISTED : TTL(-1)
    }
}