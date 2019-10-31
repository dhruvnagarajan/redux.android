package com.dhruvnagarajan.android.pubsub.producer

import com.dhruvnagarajan.android.pubsub.entity.Topic

/**
 * @author Dhruvaraj Nagarajan
 */
class Producer<T : Any>(private val topic: Topic) {

    fun publish(t: T) {
        topic.bridge.onNext(t)
    }

    private fun cache(t: T) {

    }
}