package com.brandon.kqiz.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brandon.data.local.model.QuestionEntity
import com.brandon.data.local.model.QuestionItem
import com.brandon.domain.models.Question
import com.brandon.domain.models.QuestionType
import com.brandon.domain.usecases.GetQuestionTypesAndCountUseCase
import com.brandon.domain.usecases.GetQuestionsUseCase
import com.brandon.domain.usecases.InsertQuestionsUseCase
import com.brandon.kqiz.util.Util.getRawResourceIds
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getQuestionTypesAndCountUseCase: GetQuestionTypesAndCountUseCase
) : ViewModel() {

    private val _questions = MutableStateFlow<List<QuestionType>>(emptyList())
    val questions: StateFlow<List<QuestionType>> get() = _questions

    fun getQuestions() {
        viewModelScope.launch {
            val jsonFile = getRawResourceIds()
            val question = getQuestionTypesAndCountUseCase.execute(jsonFile)
            _questions.value = question
        }
    }
}