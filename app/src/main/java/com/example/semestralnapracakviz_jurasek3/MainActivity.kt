package com.example.semestralnapracakviz_jurasek3

// Jetpack Compose imports
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable

// Navigation imports
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.semestralnapracakviz_jurasek3.ui.theme.SemestralnaPracaKviz_jurasek3Theme

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
                    composable("result") { ResultScreen(navController) }
                    //zobrazenie obrazovky s najlepsimi skore predoslych hier
                    composable("score") { ScoreboardScreen(navController) }
                }
            }
        }
    }
}