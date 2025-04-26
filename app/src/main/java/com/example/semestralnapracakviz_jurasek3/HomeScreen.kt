package com.example.semestralnapracakviz_jurasek3

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(navController: NavHostController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFBDBDBD) // svetlo šedá
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Horný oranžový box s názvom
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(Color(0xFFFF5722)), // oranžová
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Kvizio",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineLarge
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Tu zobrazíme logo.png
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo Kvizio",
                modifier = Modifier
                    .size(150.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Tlačidlo Hrať
            Button(
                onClick = { /* TODO: Prechod na QuizScreen */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722)),
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(60.dp)
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = "Hrať", tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Hrať", color = Color.White)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Tlačidlo Skóre
            Button(
                onClick = { /* TODO: Prechod na ScoreScreen */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722)),
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(60.dp)
            ) {
                Icon(Icons.Default.List, contentDescription = "Skóre", tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Skóre", color = Color.White)
            }
        }
    }
}
