package com.brandon.kqiz.ui.screens.kqiz

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brandon.domain.models.Question
import com.brandon.domain.models.QuestionType
import com.brandon.domain.usecases.LoadQuestionChunkParams
import com.brandon.domain.usecases.LoadQuestionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KqizViewModel @Inject constructor(
    private val loadQuestionsUseCase: LoadQuestionsUseCase
) : ViewModel() {

    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    val question: StateFlow<List<Question>> get() = _questions

    fun loadQuestions(params: LoadQuestionChunkParams) {
        viewModelScope.launch {
            val questions = loadQuestionsUseCase.execute(
                LoadQuestionChunkParams(
                    params.questionItemId,
                    params.chunkIndex,
                    params.chunkSize
                )
            )

            _questions.value = questions
        }
    }
}