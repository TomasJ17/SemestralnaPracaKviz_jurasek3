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

@Composable
fun ResultScreen(navController: NavController, score: Int, countOfQuestions: Int){
    //pocet otazok podla obtiaznosti
    val total = countOfQuestions;
    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFAEA9B4)) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                stringResource(R.string.result),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                stringResource(R.string.correct_answers,score,total),
                style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(40.dp))


            Button(
                onClick = { navController.navigate("quiz") },
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