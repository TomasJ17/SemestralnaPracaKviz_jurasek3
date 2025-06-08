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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

/**
 * DifficultyScreen - obrazovka výberu obtiažnosti kvízu
 * Obtiažnosť sa líši počtom otázok v kvíze
 * Ľahká 5 otázok
 * Stredná 10 otázok
 * Ťažká 15 otázok
 * */

/**
 * @param navController na prechod medzi obrazovkami
 * */
@Composable
fun DifficultyScreen(navController: NavController){
    // Surface ako pozadie celej obrazovky
    Surface(modifier = Modifier.fillMaxSize(),
        color = Color(0xFFAEA9B4)) {
    // Column pre vertikalne zobrazenie komponentov v strede obrazovky
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text( stringResource(R.string.select_difficulty)
            , style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(32.dp))

        // Tlačidlo ľahká obtiažnosť
        Button(onClick = {navController.navigate("quiz/5")},
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722)),
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(100.dp)
            ) {
            Text(
                stringResource(R.string.easy),
                fontSize = 22.sp,
                textAlign = TextAlign.Center
                )
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Tlačidlo stredná obtiažnosť
        Button(onClick = {navController.navigate("quiz/10")},
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722)),
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(100.dp)
        ) {
            Text(
                stringResource(R.string.medium),
                fontSize = 22.sp,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Tlačidlo ťažká obtiažnosť
        Button(onClick = {navController.navigate("quiz/15")},
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722)),
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(100.dp)
            ) {
            Text(
                stringResource(R.string.hard),
                fontSize = 22.sp,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Tlačidlo pre návrat na HomeScren
        Button(
            onClick = { navController.navigate("home") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722)),
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(100.dp)
        ) {
            Icon(Icons.Default.Home, contentDescription = stringResource(R.string.home), tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text(stringResource(R.string.home), color = Color.White, fontSize = 22.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
    }
}