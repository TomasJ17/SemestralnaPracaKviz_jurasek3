package com.example.semestralnapracakviz_jurasek3

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * QuizScreen - hlavná časť aplikácie, prebieha tu samotný kvíz
 *
 * Používateľovi sa zobrazujú náhodné otázky, ktoré sa načítajú  zo súboru "questions.json" a ich počet závisí od zvolenej obtiažnosti
 * Vyhodnocuje odpovede a eviduje skóre hráča, posúva sa medzi otázkami
 * Po skončení použivateľa automaticky presmeruje na ResultScreen
 * */


/**
 * @param navController na prechod medzi obrazovkami
 * @param questionCount počet otázok, ktoré sa majú v rámci kvízu zobraziť
 * */

@SuppressLint("ShowToast")
@Composable
fun QuizScreen(navController: NavHostController, questionCount: Int) {
    // Vzor na ukladanie stavu v Compose: https://developer.android.com/jetpack/compose/state#state-hoisting

    // Stavové premenne sledujúce priebeh kvízu
    var currentIndex by remember { mutableIntStateOf(0) }
    var selectedAnswerIndex by remember { mutableStateOf<Int?>(null) }
    var showCorrect by remember { mutableStateOf(false) }
    var shouldGoNext by remember { mutableStateOf(false) }
    var correctAnswers by remember { mutableIntStateOf(0) }

    // Pridané kvôli obtiažnostiam(rôzne počty otázok ľahká 5 otázok, stredná 10 otázok, ťažká 15 otázok)
    val percentageScore = (correctAnswers.toDouble() / questionCount * 100)

    // zdroj otazok "https://github.com/uberspot/OpenTriviaQA/tree/master/categories"
    val context = LocalContext.current
    val allQuestions = remember { loadQuestions(context) }

    //kontrola ci je dostatocny pocet otazok pre kviz
    if (allQuestions.size < questionCount) {
        Toast.makeText(context, "Nepodarilo sa načítať dostatočný počet otázok!", Toast.LENGTH_LONG).show()
        navController.navigate("home")
        return
    }

    // Náhodne zvolený podzoznam otázok podľa počtu otázok
    val questions = remember { allQuestions.shuffled().take(questionCount) }
    //https://stackoverflow.com/questions/71329556/method-setcurrentstate-must-be-called-on-the-main-thread-android-kotlin

    if (currentIndex >= questions.size) {
        // Odložený prechod na výsledkovú obrazovku
        LaunchedEffect(true) {
            Toast.makeText(context,"Uspesne si dokoncil kviz",Toast.LENGTH_SHORT).show()
            //pre plynuly prechod a oneskoreny presun na ďalšiu obrazovku, vytvorí nové vlákno a preto potom withContext(Dispatchers.Main)
            delay(1000)
            saveScore(context, percentageScore)
            // aby nedoslo k volaniu z ineho vlakna a naslednemu padu aplikacie, kvoli pouzitie
            withContext(Dispatchers.Main){
                navController.navigate("result/$correctAnswers/$questionCount")
            }
        }
        return
    }

    // Načítanie aktuálnej otázky
    val question = questions[currentIndex]

    // kontrola či sa správne načítali odpovede danej otázky
    if (question.answers.isEmpty()) {
        Toast.makeText(context, "Chybná otázka, nemá žiadne odpovede", Toast.LENGTH_SHORT).show()
        navController.navigate("home")
        return
    }

    // Rozloženie obrazovky
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
            // Zobrazenie textu otázky
            Text(
                text = question.text,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))


            // Vzor pre iteráciu a interaktívne komponenty: https://developer.android.com/jetpack/compose/lists
            // Odpovede ako klikateľné boxy
            question.answers.forEachIndexed { index, answer ->
                val backgroundColor = when {
                    selectedAnswerIndex == null -> Color.White
                    index == question.correctIndex && showCorrect -> Color(0xFF227E43) // zelená
                    index == selectedAnswerIndex -> Color(0xFFE43333) // červená
                    else -> Color.White
                }
                // Box pre jednotlivé možnosti odpovedí
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(backgroundColor)
                        .clickable(enabled = selectedAnswerIndex == null) {
                            selectedAnswerIndex = index
                            showCorrect = true
                            if (index == question.correctIndex) {
                                correctAnswers++
                            }
                            shouldGoNext = true
                        }
                        .padding(16.dp)
                ) {
                    Text(text = answer,
                        style = MaterialTheme.typography.titleLarge)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            Column (
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally ){
                // Tlačidlo na návrat domov
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


// efekt
                LaunchedEffect(shouldGoNext) {
                    if (shouldGoNext){
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


/**
 * Uloží dosiahnuté skóre hráča a dátum a čas dosiahnutia do súboru v lokálnom úložisku telefónu scores.txt
 *
 * @param context Kontext aplikácie, používa sa na prístup k súboru
 * @param percentageScore percentuálne skóre, ktoré sa má uložiť
 *
 * Zdroje:
 * https://developer.android.com/training/data-storage/app-specific
 * */

fun saveScore(context: Context, percentageScore: Double) {
    val filename = "scores.txt"
    val file = File(context.filesDir, filename)
    val time = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())
    val entry = "$percentageScore; $time\n"

    file.appendText(entry)
}

/**
 * Načíta otázky z JSON súboru umiestneného v assets
 *
 * @param context Kontext aplikácie
 * @return zoznam otázok ako List<Question>
 *
 * Zdroje:
 * https://www.bezkoder.com/kotlin-android-read-json-file-assets-gson/
 * https://www.geeksforgeeks.org/assets-folder-in-android/
 * https://www.baeldung.com/kotlin/gson-typetoken
 * https://developer.adobe.com/experience-manager/reference-materials/6-5/javadoc/com/google/gson/reflect/TypeToken.html
 * https://github.com/google/gson/blob/main/UserGuide.md
 *
 * TypeToken(generický typ) slúži na špecifikáciu presného typu pri deserializácii generických kolekcií
 * */

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

/**
 * Dátová tried reprezentujúca jednu otázku v kvíze
 *
 * @property text Text otázky zobrazený používateľovi
 * @property answers Zoznam možných odpovedí na otázku
 * @property correctIndex Index správnej odpovede danej otázky
 * */

data class Question(
    val text: String,
    val answers: List<String> = emptyList(),
    val correctIndex: Int = -1
)