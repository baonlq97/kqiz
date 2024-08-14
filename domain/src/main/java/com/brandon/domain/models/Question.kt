package com.brandon.domain.models

data class QuestionType (
    val id: Int,
    val name: String,
    val totalItem: Int,
    val questions: List<Question>
)

data class Question(
    val id: Int,
    val content: String,
    val answerExplanation: String,
    val options: List<QuestionOption>
)

data class QuestionOption(
    val id: Int,
    val content: String,
    val isValid: Boolean,
)