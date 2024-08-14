package com.brandon.data.local.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "question_items")
data class QuestionItem(
    @PrimaryKey val id: Int,
    val name: String,
    val totalItem: Int,
)

@Entity(
    tableName = "questions",
    foreignKeys = [
        ForeignKey(
            entity = QuestionItem::class,
            parentColumns = ["id"],
            childColumns = ["questionItemId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["questionItemId"])]
)
data class QuestionEntity(
    @PrimaryKey val id: Int,
    val questionItemId: Int,
    val content: String,
    val answerExplanation: String,
)

@Entity(
    tableName = "question_options",
    foreignKeys = [
        ForeignKey(
            entity = QuestionEntity::class,
            parentColumns = ["id"],
            childColumns = ["questionId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["questionId"])]
)
data class QuestionOptionEntity(
    @PrimaryKey val id: Int,
    val questionId: Int,
    val content: String,
    val isValid: Boolean
)