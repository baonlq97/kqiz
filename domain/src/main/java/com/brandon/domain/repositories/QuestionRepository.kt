package com.brandon.domain.repositories

import com.brandon.domain.models.Question
import com.brandon.domain.models.QuestionType
import com.brandon.domain.usecases.LoadQuestionChunkParams

interface QuestionRepository {
    suspend fun countQuestionItem(): Int
    suspend fun insertQuestions(resources: List<Int>)
    suspend fun getQuestionTypesAndCount(resources: List<Int>): List<QuestionType>
    suspend fun getQuestions(resources: List<Int>): List<QuestionType>
    suspend fun loadQuestions(params: LoadQuestionChunkParams): List<Question>
}