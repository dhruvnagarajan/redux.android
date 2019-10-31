package com.dhruvnagarajan.android.pubsub.persistence

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * @author Dhruvaraj Nagarajan
 */
@Database(
    version = 2, exportSchema = false,
    entities = [
        EventEntity::class,
        TopicMetaEntity::class
    ]
)
abstract class DB : RoomDatabase() {

    abstract fun eventDao(): EventDao

    abstract fun topicMetaDao(): TopicMetaDao
}