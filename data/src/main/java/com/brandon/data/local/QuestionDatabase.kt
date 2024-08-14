package com.brandon.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.brandon.data.local.dao.QuestionDao
import com.brandon.data.local.model.QuestionEntity
import com.brandon.data.local.model.QuestionItem
import com.brandon.data.local.model.QuestionOptionEntity

@Database(
    entities = [QuestionItem::class, QuestionEntity::class, QuestionOptionEntity::class],
    version = 1
)
abstract class QuestionDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
}