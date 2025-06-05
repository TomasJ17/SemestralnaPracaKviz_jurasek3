package com.example.semestralnapracakviz_jurasek3

import android.content.Context
import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import java.io.File
import java.io.IOException
import java.util.Date
import java.util.Locale
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Composable
fun QuizScreen(navController: NavHostController, questionCount: Int) {
    // Vzor na ukladanie stavu v Compose: https://developer.android.com/jetpack/compose/state#state-hoisting
    var currentIndex by remember { mutableStateOf(0) }
    var selectedAnswerIndex by remember { mutableStateOf<Int?>(null) }
    var showCorrect by remember { mutableStateOf(false) }
    var shouldGoNext by remember { mutableStateOf(false) }
    var correctAnswers by remember { mutableStateOf(0) }
    // Pridané kvôli obtiažnostiam(rôzne počty otázok ľahká 5 otázok, stredná 10 otázok, ťažká 15 otázok)

    val percentageScore = (correctAnswers.toDouble() / questionCount * 100).toDouble();

    // zdroj otazok "https://github.com/uberspot/OpenTriviaQA/tree/master/categories"
    val context = LocalContext.current
    val allQuestions = remember { loadQuestions(context) }
    val questions = remember { allQuestions.shuffled().take(questionCount) }

    if (currentIndex >= questions.size) {
        // Odložený prechod na výsledkovú obrazovku
        LaunchedEffect(true) {
            saveScore(context, percentageScore)
            delay(1000)
            navController.navigate("result/$correctAnswers/$questionCount")
        }
        return
    }

    val question = questions[currentIndex]

    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFAEA9B4)) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(32.dp))
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
                    index == question.correctIndex && showCorrect -> Color(0xFF227E43) // zelená
                    index == selectedAnswerIndex -> Color(0xFFE43333) // červená
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
                            if (index == question.correctIndex){
                                correctAnswers++;
                            }
                        }
                        .padding(16.dp)
                ) {
                    Text(text = answer)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            Column (
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally ){
                Button(
                    onClick = { navController.navigate("home") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722)),
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(100.dp)

                ) {
                    Icon(
                        Icons.Default.Home, contentDescription = stringResource(R.string.home),
                        tint = Color.White,
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        stringResource(R.string.home), color = Color.White,
                        fontSize = 22.sp
                    )
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


fun saveScore(context: Context, percentageScore: Double) {
    val filename = "scores.txt"
    val file = File(context.filesDir, filename)
    val time = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())
    val entry = "$percentageScore; $time\n"

    file.appendText(entry)
}

fun loadQuestions(context: Context): List<Question>{
    return try {
        val inputStream = context.assets.open("questions.json")
        val reader = inputStream.bufferedReader().use { it.readText() }
        val type = object : TypeToken<List<Question>>() {}.type
        Gson().fromJson(reader,type)
    }catch (e: IOException){
        emptyList()
    }
}

data class Question(
    val text: String,
    val answers: List<String>,
    val correctIndex: Int
)
