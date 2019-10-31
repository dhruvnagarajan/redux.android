package com.dhruvnagarajan.android.pubsub.repository

import android.content.Context
import androidx.room.Room
import com.dhruvnagarajan.android.pubsub.entity.Event
import com.dhruvnagarajan.android.pubsub.persistence.DB
import com.dhruvnagarajan.android.pubsub.persistence.toEntity
import com.google.gson.Gson

/**
 * @author Dhruvaraj Nagarajan
 */
class TopicRepositoryImpl(context: Context) : TopicRepository {

    private val db = Room.databaseBuilder(context, DB::class.java, "pubsub")
        .fallbackToDestructiveMigration()
        .build()

    private val dao = db.eventDao()

    override fun <T> getAllEvents(clazz: Class<T>): List<T> {
        val results = dao.getAll()
        val returnList = ArrayList<T>()

        for (result in results) {
            val e = Gson().fromJson(result.payload, clazz)
            returnList.add(e)
        }

        return returnList
    }

    override fun <T> getEventsAfterMostRecentOccurrence(lastEventTimestamp: Long) {
        TODO("not implemented")
    }

    override fun <T> getEventsAfterMostRecentOccurrence(lastEvent: Event) {
        TODO("not implemented")
    }

    override fun createEvent(event: Event) {
        dao.insert(event.toEntity())
    }

    override fun deleteEvent(timestamp: Long) {
        TODO("not implemented")
    }
}