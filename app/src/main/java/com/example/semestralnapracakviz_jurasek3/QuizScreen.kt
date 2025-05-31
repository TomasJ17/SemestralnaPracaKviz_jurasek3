package com.example.semestralnapracakviz_jurasek3

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay

@Composable
fun QuizScreen(navController: NavHostController) {
    // Vzor na ukladanie stavu v Compose: https://developer.android.com/jetpack/compose/state#state-hoisting
    var currentIndex by remember { mutableStateOf(0) }
    var selectedAnswerIndex by remember { mutableStateOf<Int?>(null) }
    var showCorrect by remember { mutableStateOf(false) }
    var shouldGoNext by remember { mutableStateOf(false) }

    // Otázky a odpovede (bude sa neskôr načítať zo súboru)
    val questions = listOf(
        Question(
            text = "Ktoré mesto je hlavné mesto Slovenska?",
            answers = listOf("Košice", "Bratislava", "Prešov", "Nitra"),
            correctIndex = 1
        ),
        Question(
            text = "Ktorý jazyk je primárny pre Android vývoj?",
            answers = listOf("Java", "Swift", "Kotlin", "Python"),
            correctIndex = 2
        )
    )

    if (currentIndex >= questions.size) {
        // Odložený prechod na výsledkovú obrazovku
        LaunchedEffect(true) {
            delay(1000)
            navController.navigate("result")
        }
        return
    }

    val question = questions[currentIndex]

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Otázka ${currentIndex + 1}/${questions.size}",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = question.text,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Vzor pre iteráciu a interaktívne komponenty: https://developer.android.com/jetpack/compose/lists
            question.answers.forEachIndexed { index, answer ->
                val backgroundColor = when {
                    selectedAnswerIndex == null -> Color.LightGray
                    index == question.correctIndex && showCorrect -> Color(0xFF81C784) // zelená
                    index == selectedAnswerIndex -> Color(0xFFE57373) // červená
                    else -> Color.LightGray
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(backgroundColor)
                        .clickable(enabled = selectedAnswerIndex == null) {
                            selectedAnswerIndex = index
                            showCorrect = true
                            shouldGoNext = true
                        }
                        .padding(16.dp)
                ) {
                    Text(text = answer)
                }
            }

// efekt musí byť mimo kliknutia:
            if (shouldGoNext) {
                LaunchedEffect(currentIndex) {
                    delay(1500)
                    currentIndex++
                    selectedAnswerIndex = null
                    showCorrect = false
                    shouldGoNext = false
                }
            }
        }
    }
}

data class Question(
    val text: String,
    val answers: List<String>,
    val correctIndex: Int
)
