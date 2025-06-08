package com.example.semestralnapracakviz_jurasek3

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.io.File
import java.util.Locale

/**
 * ScoreBoardScreen - Zobrazovanie rebríčka TOP výsledkov v percentách spolu s dítumom a časom
 *
 * Zobrazí zoznam predchádzajúcich TOP výsledkov zoradených od najlepšieho, dáta sa načítavajú z lokálneho úložiska telefónu
 * */

/**
 * @param navController na prechod medzi obrazovkami
 * */

@Composable
fun ScoreBoardScreen(navController: NavController){
    // Ziskanie kontextu aplikacie
    val context = LocalContext.current
    // Nacitanie skore len raz pri vykreslenie, zabezpecene pomocou remember
    val scores = remember { readScores(context) }
    // Hlavná obrazovka
    Surface(modifier = Modifier.fillMaxSize(),
        color = Color(0xFFAEA9B4)) {
        Column (
            modifier = Modifier.fillMaxSize()
                .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
        ){
            Spacer(modifier = Modifier.height(48.dp))
            // Nadpis Tvoje TOP skóre
            Text(
                stringResource(R.string.your_top_scores),
                style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(120.dp))
            // Zobrazeneie TOP 5 výsledkov z lokálneho zonamu, ktorý dostaneme od funkcie fun readScores
            scores.take(5).forEachIndexed{
                index, (score, date) ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    // Ikonky trofeji(hviezdičiek) prvé tri miesta v príslušnej farbe zlatá, strieborná, bronzová
                    if (index == 0){
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = stringResource(R.string.trophy_first),
                            tint = Color(0xFFFFCC00),
                            modifier = Modifier.size(32.dp)
                                .padding(end = 8.dp)
                        )
                    }
                    if (index == 1){
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = stringResource(R.string.trophy_second),
                            tint = Color(0xFFDBDCDB),
                            modifier = Modifier.size(32.dp)
                                .padding(end = 8.dp)
                        )
                    }
                    if (index == 2){
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = stringResource(R.string.trophy_third),
                            tint = Color(0xFFAC781D),
                            modifier = Modifier.size(32.dp)
                                .padding(end = 8.dp)
                        )
                    }
                    // Výpis poradia 1., 2., 3.
                    Text(
                        // Locale.ROOT pre konzistenciu vypisu, stale s . bez ohľadu na jazyk androidu
                        text = String.format(Locale.ROOT,"%2d.", index +1),
                        modifier = Modifier.width(32.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 24.sp,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    // Výpis skóre v percentách a dátumu
                    Text(
                        text = "$score% - $date",
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 24.sp,
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
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
                Text(stringResource(R.string.home),
                    color = Color.White,
                    fontSize = 22.sp
                    )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

/**
 * Načíta skóre používateľa zo súboru a vracia ich ako zoradený zoznam párov
 *
 * skóre - dátum (Zoradené zostupne(od najväčšieho))
 *
 * @param context Kontext aplikácie (potrebný pre prístup k internému úložisku).
 * @return List<Pair<Double, String>> Zoznam párov (skóre, dátum) reprezentujúcich uložené výsledky,
 *         zoradený zostupne podľa skóre (najvyššie ako prvé).
 *         Napr. Pair(85.0, "2025-06-06").
 *         Zdroje:
 *         https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.io/read-lines.html
 *         https://developer.android.com/training/data-storage/app-specific
 *         https://ajaydeepak.medium.com/kotlin-string-split-trim-substring-fad3bbb37649
 */

fun readScores(context: Context): List<Pair<Double, String>> {
    val filename = "scores.txt"
    val file = File(context.filesDir, filename)
    // Ak súbor neexistuje vrátime hneď prázdny zoznam
    if (!file.exists()) return emptyList()
    // Spracovanie riadkov, rozdelenie na skóre a dátum a zoradenie
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