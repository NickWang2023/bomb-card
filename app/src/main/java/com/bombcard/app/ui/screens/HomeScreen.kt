package com.bombcard.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bombcard.app.data.CardRepository
import com.bombcard.app.data.SoulCard
import com.bombcard.app.ui.components.BombCard
import com.bombcard.app.ui.components.DateHeader
import com.bombcard.app.ui.components.WeatherWidget
import com.bombcard.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    var currentCard by remember { mutableStateOf(CardRepository.getRandomCard()) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "💣 心灵炸弹",
                        style = MaterialTheme.typography.headlineMedium,
                        color = WarmWhite,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DeepOrange,
                    titleContentColor = WarmWhite
                )
            )
        },
        bottomBar = {
            WeatherWidget()
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WarmWhite)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Date Header
            DateHeader()
            
            // Title
            Text(
                text = "今日心灵炸弹",
                style = MaterialTheme.typography.headlineSmall,
                color = DeepOrange,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            
            // Bomb Card
            BombCard(
                card = currentCard,
                onRefresh = {
                    var newCard = CardRepository.getRandomCard()
                    while (newCard.id == currentCard.id) {
                        newCard = CardRepository.getRandomCard()
                    }
                    currentCard = newCard
                }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Motivational quote at bottom
            Text(
                text = "💡 每天一颗心灵炸弹，引爆你的正能量！",
                style = MaterialTheme.typography.labelMedium,
                color = LightText,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
            )
            
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}