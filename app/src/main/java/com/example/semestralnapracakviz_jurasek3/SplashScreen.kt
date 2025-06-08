package com.example.semestralnapracakviz_jurasek3

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

/**
* SplashScreen - úvodná(načítavacia) obrazovka aplikácie
 *
 * Zobrazí sa používateľovi na začiatku po spustení a privíta ho
 * Po krátkej chvíli používateľa presmeruje na HomeScreen
* */

/**
 * @param navController na prechod medzi obrazovkami
 * */

@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(2000) // 2 sekundy zobrazenie Splash obrazovky
        navController.navigate("home") {
            popUpTo("splash") { inclusive = true }
        }
    }

    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(){
                Text(
                    stringResource(R.string.welcome),
                    fontSize = 96.sp)
            }
            // Zobrazenie loga (logo.png)
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(R.string.logo_desc),
                modifier = Modifier.size(150.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Názov aplikácie
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}
