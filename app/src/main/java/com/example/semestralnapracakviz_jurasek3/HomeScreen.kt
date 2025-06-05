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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(navController: NavHostController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFAEA9B4)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
               // .padding(16.dp),

        ) {
            Spacer(Modifier.height(48.dp))
            // Horný oranžový box s názvom
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .background(Color(0xFFFF5722)), // oranžová
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.app_name),
                    color = Color.White,
                    style = MaterialTheme.typography.headlineLarge,
                    fontSize = 64.sp
                )
            }

            // Tu zobrazíme logo.png
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                    //.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(R.string.logo_desc),
                modifier = Modifier
                    .size(300.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Tlačidlo Hrať
            Button(
                onClick = { navController.navigate("difficulty") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722)),
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(100.dp)
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = stringResource(R.string.play),
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                    )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    stringResource(R.string.play), color = Color.White,
                        fontSize = 22.sp
                    )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Tlačidlo Skóre
            Button(
                onClick = { navController.navigate("score") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722)),
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(100.dp)
            ) {
                Icon(Icons.Default.List, contentDescription = stringResource(R.string.score),
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                    )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    stringResource(R.string.score), color = Color.White,
                        fontSize = 22.sp
                    )
            }
        }
    }
    }
}
