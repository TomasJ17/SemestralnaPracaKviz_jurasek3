package com.example.semestralnapracakviz_jurasek3

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.io.File

@SuppressLint("DefaultLocale")
@Composable
fun ScoreBoardScreen(navController: NavController){
    val context = LocalContext.current
    val scores = remember { readScores(context) }

    Surface(modifier = Modifier.fillMaxSize(),
        color = Color(0xFFAEA9B4)) {
        Column (
            modifier = Modifier.fillMaxSize()
                .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
        ){
            Spacer(modifier = Modifier.height(48.dp))
            Text("Tvoje TOP skóre", style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(120.dp))
            // preto lebo dvojciferne cisla mi rozbili obrazovku
            scores.take(9).forEachIndexed{//index + 1 aby slo cislovanie od 1 a nie od 0
                index, (score, date) ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    if (index == 0){
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Trofej 1.miesto",
                            tint = Color(0xFFFFCC00),
                            modifier = Modifier.size(32.dp)
                                .padding(end = 8.dp)
                        )
                    }
                    if (index == 1){
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Trofej 2.miesto",
                            tint = Color(0xFFDBDCDB),
                            modifier = Modifier.size(32.dp)
                                .padding(end = 8.dp)
                        )
                    }
                    if (index == 2){
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Trofej 3.miesto",
                            tint = Color(0xFFAC781D),
                            modifier = Modifier.size(32.dp)
                                .padding(end = 8.dp)
                        )
                    }
                    Text(
                        text = String.format("%2d.", index +1),
                        modifier = Modifier.width(32.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 24.sp,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "$score% - $date",
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 24.sp,
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { navController.navigate("home") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722)),
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(100.dp)
            ) {
                Icon(Icons.Default.Home, contentDescription = "Domov", tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Domov", color = Color.White,
                        fontSize = 22.sp
                    )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
fun readScores(context: Context): List<Pair<Double, String>> {
    val filename = "scores.txt"
    val file = File(context.filesDir, filename)

    if (!file.exists()) return emptyList()
    return file.readLines()
        .mapNotNull {
        val substrs = it.split(";")
        if (substrs.size == 2){
            val score = substrs[0].trim().toDoubleOrNull()
            val date = substrs[1].trim()
            if (score != null) Pair(score, date) else null
        } else null
    }
        .sortedByDescending { it.first }
}