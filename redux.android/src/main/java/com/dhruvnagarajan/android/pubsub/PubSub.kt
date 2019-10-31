package com.dhruvnagarajan.android.pubsub

import android.content.Context
import androidx.room.Room
import com.dhruvnagarajan.android.pubsub.entity.Topic
import com.dhruvnagarajan.android.pubsub.persistence.DB
import com.dhruvnagarajan.android.pubsub.persistence.toPojo

/**
 * @param context application context
 *
 * @author Dhruvaraj Nagarajan
 */
class PubSub(val context: Context) {

    // todo remove
    private val db = Room.databaseBuilder(
        context,
        DB::class.java, DB::class.java.name
    ).build()

    private val metaDao = db.topicMetaDao()

    private val topics = HashMap<String, Topic>()

    fun init() {
        loadTopicsFromDisk()
    }

    private fun loadTopicsFromDisk() {
        val existingTopics = metaDao.getAll()
        for (entity in existingTopics) {
            val pojo = entity.toPojo()
            val topicName = pojo.topicName
            topics[topicName] = createTopic(topicName, pojo.eventTtl)
        }
    }
    
    fun createTopic(
        topicName: String,
        ttl: Topic.TTL = Topic.TTL.REMOVE_ON_CONSUMPTION
    ): Topic {
        if (topics[topicName] != null) throw Exception("Topic already registered")
        val topic = Topic(topicName, ttl, context)
        topics[topicName] = topic
        return topic
    }

    fun getTopic(topicName: String): Topic {
        if (topics[topicName] == null) throw Exception("Topic not found")
        return topics[topicName]!!
    }
}