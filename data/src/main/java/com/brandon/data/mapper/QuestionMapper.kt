package com.brandon.data.mapper

import com.brandon.data.local.dao.QuestionItems
import com.brandon.data.local.dao.QuestionWithOptions
import com.brandon.data.local.model.QuestionEntity
import com.brandon.data.local.model.QuestionItem
import com.brandon.data.local.model.QuestionOptionEntity
import com.brandon.data.mapper.Mapper.toDomainModel
import com.brandon.data.model.QuestionJsonData
import com.brandon.data.model.QuestionOptionJsonData
import com.brandon.domain.models.Question
import com.brandon.domain.models.QuestionOption
import com.brandon.domain.models.QuestionType

object Mapper {
    fun QuestionJsonData.toQuestionEntity(questionItem: QuestionItem): QuestionEntity {
        return QuestionEntity(
            id = this.id,
            questionItemId = questionItem.id,
            content = this.attributes.content,
            answerExplanation = this.attributes.answerExplanation ?: ""
        )
    }

    fun QuestionOptionJsonData.toQuestionOptionEntity(questionModel: QuestionJsonData): QuestionOptionEntity {
        return QuestionOptionEntity(
            id = this.id,
            questionId = questionModel.id,
            content = this.content,
            isValid = this.isValid
        )
    }

    fun QuestionItem.toDomainModel(): QuestionType {
        return QuestionType(
            id = this.id,
            name = this.name,
            totalItem = this.totalItem,
            questions = emptyList()
        )
    }

    fun QuestionItems.toDomainModel(): QuestionType {
        return QuestionType(
            id = this.questionItem.id,
            name = this.questionItem.name,
            totalItem = this.questionItem.totalItem,
            questions = this.questions.map { it.toDomainModel() }
        )
    }

    fun QuestionWithOptions.toDomainModel(): Question {
        return Question(
            id = this.question.id,
            content = this.question.content,
            answerExplanation = this.question.answerExplanation,
            options = this.options.map { it.toDomainModel() }
        )
    }

    fun QuestionOptionEntity.toDomainModel(): QuestionOption {
        return QuestionOption(
            id = this.id,
            content = this.content,
            isValid = this.isValid
        )
    }
}
