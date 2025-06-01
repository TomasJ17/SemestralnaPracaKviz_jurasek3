package com.example.semestralnapracakviz_jurasek3

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            Surface(color = MaterialTheme.colorScheme.background) {
                NavHost(navController = navController, startDestination = "splash") {
                    //kratka uvodna obrazovka
                    composable("splash") { SplashScreen(navController) }
                    //domovska obrazovka s menu a tlacidlami
                    composable("home") { HomeScreen(navController) }
                    //zobrazuje otazky, odpovede a po skonceni ukaze result screen
                    composable("quiz") { QuizScreen(navController) }
                    //zobrazi vysledok jednej danej hry, pocet spravnych odpovedi
                    composable("result/{score}") { backStackEntry ->
                        val score = backStackEntry.arguments?.getString("score")?.toIntOrNull() ?: 0
                        ResultScreen(navController, score) }
                    //zobrazenie obrazovky s najlepsimi skore predoslych hier
                    composable("score") { ScoreBoardScreen(navController) }

                }
            }
        }
    }
}