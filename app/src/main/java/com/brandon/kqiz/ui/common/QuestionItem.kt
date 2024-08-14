package com.brandon.kqiz.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.brandon.domain.models.QuestionOption

@Composable
fun MultipleOptionQuestion(
    question: String,
    options: List<QuestionOption>,
    selectedOptionsId: MutableList<Int>?,
    onOptionsSelected: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = question,
            style = MaterialTheme.typography.titleMedium,
            fontFamily = FontFamily.Default,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxSize()
        )

        options.forEach { option ->
            val isCheck = selectedOptionsId?.contains(option.id) ?: false
            Card(
//                border = BorderStroke(2.dp, borderColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .clickable(onClick = {
                        onOptionsSelected(option.id)
                    }),
                shape = RoundedCornerShape(2.dp),
                elevation = CardDefaults.cardElevation(4.dp),

                ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxSize()
                        .defaultMinSize(minHeight = 42.dp)
                ) {
                    Checkbox(
                        checked = isCheck,
                        onCheckedChange = {
                            onOptionsSelected(option.id)
                        },

                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = option.content,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun SingleOptionQuestion(
    question: String,
    options: List<QuestionOption>,
    selectedOptionId: Int?,
    onOptionSelected: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = question,
            style = MaterialTheme.typography.bodyMedium,
            fontFamily = FontFamily.Default,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxSize()
        )

        options.forEachIndexed { index, option ->
            SingleOptionCard(
                optionText = option.content,
                isSelected = selectedOptionId == index,
                shouldShowResult = selectedOptionId != null,
                isCorrect = option.isValid,
                onClick = { onOptionSelected(index) }
            )
        }
    }
}

@Composable
fun SingleOptionCard(
    optionText: String,
    isSelected: Boolean,
    shouldShowResult: Boolean,
    isCorrect: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val borderColor = when {
        !shouldShowResult -> MaterialTheme.colorScheme.primaryContainer
        isCorrect -> MaterialTheme.colorScheme.primary
        !isCorrect && isSelected -> MaterialTheme.colorScheme.error
        else -> MaterialTheme.colorScheme.primaryContainer
    }

    var isExpanded by remember { mutableStateOf(false) }

    Card(
        border = BorderStroke(2.dp, borderColor),
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),

        ) {
        Text(
            text = optionText,
            modifier = Modifier.padding(16.dp)
        )

        if (isCorrect && shouldShowResult) {
            if (isExpanded) {
                TextButton(modifier = modifier, onClick = { isExpanded = false }) {
                    Text("Collapse")
                }

                Text(
                    modifier = modifier.padding(16.dp),
                    text = "content",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(8.dp))
            } else {
                TextButton(onClick = { isExpanded = true }) {
                    Text("Expand")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuestionPRs() {
    Column {
        MultipleOptionQuestion(
            question = "Test",
            options = mutableListOf(
                QuestionOption(0, "Test1", false),
                QuestionOption(1, "Test2", true)
            ),
            selectedOptionsId = mutableListOf(0, 1)
        ) { id ->

        }

//        SingleOptionQuestion(
//            question = "Test",
//            options = mutableListOf(
//                QuestionOption(0, "Test1", false),
//                QuestionOption(1, "Test2", true)
//            ),
//            selectedOptionId = 0
//        ) {
//
//        }
    }

}