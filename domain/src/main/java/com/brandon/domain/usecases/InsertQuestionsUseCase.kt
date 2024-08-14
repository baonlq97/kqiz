package com.brandon.domain.usecases

import com.brandon.domain.repositories.QuestionRepository
import javax.inject.Inject

class InsertQuestionsUseCase @Inject constructor(
    private val repository: QuestionRepository
) : UseCase<List<Int>, Unit>() {
    override suspend fun execute(params: List<Int>) {
        repository.insertQuestions(params)
    }
}