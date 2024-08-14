package com.brandon.data.repositories

import com.brandon.data.helper.JsonLoader
import com.brandon.data.local.datasource.LocalDataSource
import com.brandon.data.local.model.QuestionItem
import com.brandon.data.mapper.Mapper.toDomainModel
import com.brandon.data.mapper.Mapper.toQuestionEntity
import com.brandon.data.mapper.Mapper.toQuestionOptionEntity
import com.brandon.data.model.QuestionJsonData
import com.brandon.domain.models.Question
import com.brandon.domain.models.QuestionType
import com.brandon.domain.repositories.QuestionRepository
import com.brandon.domain.usecases.LoadQuestionChunkParams
import javax.inject.Inject

class QuestionRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val jsonLoader: JsonLoader,
) : QuestionRepository {

    override suspend fun countQuestionItem(): Int {
        return localDataSource.countQuestionItem()
    }

    override suspend fun insertQuestions(resources: List<Int>) {
        var allQuestions: MutableList<QuestionJsonData>
        var resourceCount = 0

        resources.forEach { resId ->
            allQuestions = emptyList<QuestionJsonData>().toMutableList()
            resourceCount += 1
            val questionJsonModel = jsonLoader.loadAllJsonFromRaw(resId)
            val questionItem = QuestionItem(
                resourceCount,
                questionJsonModel.meta.name,
                questionJsonModel.meta.pagination.total
            )
            localDataSource.insertQuestionItem(questionItem)

            allQuestions.addAll(questionJsonModel.data)

            val questionEntities = allQuestions.map { it.toQuestionEntity(questionItem) }
            localDataSource.insertQuestions(questionEntities)

            val optionEntities = allQuestions.flatMap { question ->
                question.attributes.options.map { it.toQuestionOptionEntity(question) }
            }
            localDataSource.insertOptions(optionEntities)
        }
    }

    override suspend fun getQuestionTypesAndCount(resources: List<Int>): List<QuestionType> {
        if (countQuestionItem() == 0) {
            insertQuestions(resources)
        }

        return localDataSource.getQuestionTypesAndCount().map {
            it.toDomainModel()
        }
    }

    override suspend fun getQuestions(resources: List<Int>): List<QuestionType> {
        if (countQuestionItem() == 0) {
            insertQuestions(resources)
        }

        return localDataSource.getAllQuestionsWithOptions().map {
            it.toDomainModel()
        }
    }

    override suspend fun loadQuestions(params: LoadQuestionChunkParams): List<Question> {
        return localDataSource.loadQuestions(params).map {
            it.toDomainModel()
        }
    }
}