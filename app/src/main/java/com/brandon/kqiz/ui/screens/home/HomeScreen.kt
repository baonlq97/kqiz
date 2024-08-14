package com.brandon.kqiz.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.brandon.domain.models.Question
import com.brandon.domain.models.QuestionType
import com.brandon.kqiz.R
import com.brandon.kqiz.ui.common.CustomTopAppBar
import com.brandon.kqiz.ui.navigation.Screens
import com.brandon.kqiz.util.QUESTION_CHUNK
import kotlinx.coroutines.delay
import kotlin.math.ceil

@Composable
fun HomeScreen(
    navController: NavController
) {
    val viewModel: HomeViewModel = hiltViewModel()

    LaunchedEffect(key1 = true) {
        delay(200)
        viewModel.getQuestions()
    }

    HomeScaffold(navController, viewModel)
}

@Composable
private fun HomeScaffold(navController: NavController, viewModel: HomeViewModel) {
    val questions by viewModel.questions.collectAsState()

    Scaffold(
        topBar = {
            CustomTopAppBar(headerText = "Home", iconRes = R.drawable.ic_nav_categories)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (questions.isNotEmpty()) {
                ExpandableQuestionList(navController, questions)
            } else {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun ExpandableQuestionList(
    navController: NavController,
    questionTypes: List<QuestionType>,
//    onQuestionClicked: (List<QuestionEntity>) -> Unit
) {
    LazyColumn {
        items(questionTypes) { questionType ->
            ExpandableItem(questionType = questionType) { id, index ->
                navController.navigate(
                    Screens.KqizScreen.withTestIdAndIndex(
                        id.toString(), index.toString()
                    )
                )
            }
        }
    }
}

@Composable
fun ExpandableItem(
    questionType: QuestionType,
    onQuestionClicked: (Int, Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = questionType.name,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(16.dp)
        )

        AnimatedVisibility(visible = expanded) {
            Column {
                val totalButton = ceil(questionType.totalItem.toDouble() / QUESTION_CHUNK).toInt()

                repeat(totalButton) { index ->
                    OutlinedButton(
                        onClick = {
                            onQuestionClicked(questionType.id, index)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Show Questions ${index + 1}")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPR() {
    HomeScreen(navController = rememberNavController())
}