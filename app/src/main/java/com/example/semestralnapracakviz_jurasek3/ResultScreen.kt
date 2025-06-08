package com.example.semestralnapracakviz_jurasek3

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

/**
 * ResultScreen - zobrazuje výsledné skóre kvízu po jeho skončení
 * Formát skóre je v štýle správne odpovede/ celkový počet otázok
 * Používateľ má možnosť vrátiť sa späť na domovskú obrazovku, alebo si zobraziť rebríček jeho TOP skóre
 * */


/**
 * @param navController Navigation controller pre návrat späť alebo zobrazenie ScoreBoard.
 * @param score Počet správnych odpovedí, ktoré používateľ získal.
 * @param countOfQuestions Celkový počet otázok v danom kvíze.
 * */
@Composable
fun ResultScreen(navController: NavController, score: Int, countOfQuestions: Int){
    // Počet otázok v kvíze na základe obtiažnosti
    val total = countOfQuestions
    // Surface ako pozadie celej obrazovky
    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFAEA9B4)) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            // Nadpis
            Text(
                stringResource(R.string.result),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(24.dp))
            // Text ako skóre vo formáte Správne odpovede: X/Y
            Text(
                stringResource(R.string.correct_answers,score,total),
                style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(40.dp))

            // Tlačidlo pre spustenie nového kvízu
            Button(
                onClick = { navController.navigate("difficulty") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722)),
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(100.dp)
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = stringResource(R.string.play_again), tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    stringResource(R.string.play_again),
                    color = Color.White,
                    fontSize = 22.sp
                    )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Tlačidlo na zobrazenie skore presmerovanie na ScoreBoardScreen
            Button(
                onClick = { navController.navigate("score") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722)),
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(100.dp)
            ) {
                Icon(Icons.Default.Menu, contentDescription = stringResource(R.string.score), tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    stringResource(R.string.view_score),
                    color = Color.White,
                    fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Tlačidlo pre návrat na hlavnú obrazovku
            Button(
                onClick = { navController.navigate("home") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722)),
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(100.dp)
            ) {
                Icon(Icons.Default.Home, contentDescription = stringResource(R.string.home),
                    tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    stringResource(R.string.home),
                    color = Color.White,
                    fontSize = 22.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}