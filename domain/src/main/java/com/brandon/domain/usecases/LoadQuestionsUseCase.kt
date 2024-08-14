package com.brandon.domain.usecases

import com.brandon.domain.models.Question
import com.brandon.domain.repositories.QuestionRepository
import javax.inject.Inject

data class LoadQuestionChunkParams(
    val questionItemId: Int,
    val chunkIndex: Int,
    val chunkSize: Int
)

class LoadQuestionsUseCase @Inject constructor(
    private val localQuestionRepository: QuestionRepository
): UseCase<LoadQuestionChunkParams, List<Question>>() {
    override suspend fun execute(params: LoadQuestionChunkParams): List<Question> {
        return localQuestionRepository.loadQuestions(params)
    }
}