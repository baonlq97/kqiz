package com.brandon.kqiz.ui.screens.kqiz

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.brandon.domain.models.Question
import com.brandon.domain.models.QuestionOption
import com.brandon.domain.usecases.LoadQuestionChunkParams
import com.brandon.kqiz.R
import com.brandon.kqiz.ui.common.CustomTopAppBar
import com.brandon.kqiz.ui.common.MultipleOptionQuestion
import com.brandon.kqiz.ui.common.SingleOptionQuestion
import com.brandon.kqiz.util.QUESTION_CHUNK
import kotlinx.coroutines.launch

@Composable
fun KqizScreen(navController: NavController, questionId: String, questionIndex: String) {

    val viewModel: KqizViewModel = hiltViewModel()

    LaunchedEffect(key1 = true) {
        viewModel.loadQuestions(
            LoadQuestionChunkParams(
                questionItemId = questionId.toInt(),
                chunkIndex = questionIndex.toInt(),
                chunkSize = QUESTION_CHUNK
            )
        )
    }

    KqizScaffold(navController, viewModel)
}

@Composable
fun KqizScaffold(navController: NavController, viewModel: KqizViewModel) {
    val questions by viewModel.question.collectAsState()

    Scaffold(topBar = {
        CustomTopAppBar(headerText = "Kqiz") {
            navController.navigateUp()
        }
    }) { paddingValues ->
        if (questions.isNotEmpty()) {
            QuestionPager(questions = questions, paddingValues = paddingValues)
        } else {
            CircularProgressIndicator()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuestionPager(
    questions: List<Question>,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues
) {
    var currentIndex by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState(pageCount = { questions.count() })
    val selectedOptions = remember { mutableStateMapOf<Int?, Int?>() }

    val selectedMultipleOptions = remember { mutableStateMapOf<Int?, MutableList<Int>?>() }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            currentIndex = page
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            val question = questions[page]
            if (question.options.count { p -> p.isValid } > 1) {
                MultipleOptionQuestion(
                    question = question.content,
                    options = question.options,
                    selectedOptionsId = selectedMultipleOptions[page],
                    onOptionsSelected = { id ->
                        val currentSelectedOptions =
                            selectedMultipleOptions[page] ?: mutableListOf()

                        if (!currentSelectedOptions.contains(id)) {
                            currentSelectedOptions.add(id)
                        } else {
                            currentSelectedOptions.remove(id)
                        }

                        selectedMultipleOptions.remove(page)
                        selectedMultipleOptions[page] = currentSelectedOptions
                    }
                )
            } else {
                SingleOptionQuestion(
                    question = question.content,
                    options = question.options,
                    selectedOptionId = selectedOptions[page],
                    onOptionSelected = { id ->
                        selectedOptions[page] = id
                    }
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    if (currentIndex > 0) {
                        currentIndex -= 1
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(currentIndex)
                        }
                    }
                },
                enabled = currentIndex > 0
            ) {
                Text("Previous")
            }
            Button(
                onClick = {
                    if (currentIndex < questions.size - 1) {
                        currentIndex += 1
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(currentIndex)
                        }
                    }
                },
                enabled = currentIndex < questions.size - 1
            ) {
                Text("Next")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KqizScreen(rememberNavController(), "1", "65");
}