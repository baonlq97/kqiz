package com.brandon.domain.usecases

import com.brandon.domain.models.QuestionType
import com.brandon.domain.repositories.QuestionRepository
import javax.inject.Inject

class GetQuestionTypesAndCountUseCase @Inject constructor(
    private val localQuestionRepository: QuestionRepository
) : UseCase<List<Int>, List<QuestionType>>() {

    override suspend fun execute(params: List<Int>): List<QuestionType> {
        return localQuestionRepository.getQuestionTypesAndCount(params)
    }
}