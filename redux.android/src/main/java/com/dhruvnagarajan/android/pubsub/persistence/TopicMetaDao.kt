package com.dhruvnagarajan.android.pubsub.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/**
 * @author dhruvaraj nagarajan
 */
@Dao
interface TopicMetaDao {

    @Query("SELECT * FROM TopicMetaEntity")
    fun getAll(): List<TopicMetaEntity>

    @Insert
    fun insert(topicMetaEntity: TopicMetaEntity)

    @Delete
    fun delete(topicMetaEntity: TopicMetaEntity)
}