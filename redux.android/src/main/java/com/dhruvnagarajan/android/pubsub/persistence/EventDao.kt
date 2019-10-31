package com.dhruvnagarajan.android.pubsub.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


/**
 * @author Dhruvaraj Nagarajan
 */
@Dao
interface EventDao {

    @Query("SELECT * FROM EventEntity WHERE timestamp > :mostRecentEventTimestamp")
    fun getAllWhere(mostRecentEventTimestamp: Long): List<EventEntity>

    @Query("SELECT * FROM EventEntity")
    fun getAll(): List<EventEntity>

    @Insert
    fun insert(eventEntity: EventEntity)

    @Query("DELETE FROM EventEntity where timestamp <= :timestamp")
    fun deleteUptilTimestamp(timestamp: Long)
}