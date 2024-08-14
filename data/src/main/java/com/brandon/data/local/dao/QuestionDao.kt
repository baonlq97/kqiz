package com.brandon.data.local.dao

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.Junction
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import com.brandon.data.local.model.QuestionEntity
import com.brandon.data.local.model.QuestionItem
import com.brandon.data.local.model.QuestionOptionEntity

@Dao
interface QuestionDao {

    @Query("SELECT COUNT(*) FROM question_items")
    suspend fun countQuestionItem(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestionItem(questionItem: QuestionItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<QuestionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestionOptions(options: List<QuestionOptionEntity>)

    @Query("SELECT * FROM question_items")
    suspend fun getQuestionTypesAndCount(): List<QuestionItem>

    @Transaction
    @Query("SELECT * FROM question_items")
    suspend fun getAllQuestions(): List<QuestionItems>

    @Transaction
    @Query("SELECT * FROM questions WHERE questionItemId = :questionItemId LIMIT :limit OFFSET :offset")
    suspend fun loadQuestions(questionItemId: Int, limit: Int, offset: Int): List<QuestionWithOptions>
}

data class QuestionItems(
    @Embedded val questionItem: QuestionItem,
    @Relation(
        entity = QuestionEntity::class,
        parentColumn = "id",
        entityColumn = "questionItemId"
    )
    val questions: List<QuestionWithOptions>
)

data class QuestionWithOptions(
    @Embedded val question: QuestionEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "questionId",
    )
    val options: List<QuestionOptionEntity>
)