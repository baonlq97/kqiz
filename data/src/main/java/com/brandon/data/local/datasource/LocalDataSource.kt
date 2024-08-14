package com.brandon.data.local.datasource

import com.brandon.data.local.dao.QuestionDao
import com.brandon.data.local.dao.QuestionItems
import com.brandon.data.local.dao.QuestionWithOptions
import com.brandon.data.local.model.QuestionEntity
import com.brandon.data.local.model.QuestionItem
import com.brandon.data.local.model.QuestionOptionEntity
import com.brandon.domain.models.Question
import com.brandon.domain.usecases.LoadQuestionChunkParams
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val questionDao: QuestionDao
) {
    suspend fun countQuestionItem(): Int {
        return questionDao.countQuestionItem()
    }

    suspend fun insertQuestionItem(questionItem: QuestionItem) {
        questionDao.insertQuestionItem(questionItem)
    }

    suspend fun insertQuestions(questions: List<QuestionEntity>) {
        try {
            questionDao.insertQuestions(questions)
        }
        catch (ex: Exception) {
            print(ex)
        }
    }

    suspend fun insertOptions(options: List<QuestionOptionEntity>) {
        questionDao.insertQuestionOptions(options)
    }

    suspend fun getQuestionTypesAndCount(): List<QuestionItem> {
        return questionDao.getQuestionTypesAndCount()
    }

    suspend fun getAllQuestionsWithOptions(): List<QuestionItems> {
        return questionDao.getAllQuestions()
    }

    suspend fun loadQuestions(params: LoadQuestionChunkParams): List<QuestionWithOptions> {
        val offset = params.chunkIndex * params.chunkSize
        return questionDao.loadQuestions(params.questionItemId, params.chunkSize, offset)
    }
}