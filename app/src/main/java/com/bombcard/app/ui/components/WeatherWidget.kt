package com.bombcard.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bombcard.app.ui.theme.*

@Composable
fun WeatherWidget() {
    // Placeholder weather data - in production, fetch from weather API
    Surface(
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        color = DeepOrange.copy(alpha = 0.08f),
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Weather icon and temp
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.WbSunny,
                    contentDescription = "天气",
                    tint = WarmYellow,
                    modifier = Modifier.size(32.dp)
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column {
                    Text(
                        text = "26°C",
                        style = MaterialTheme.typography.titleLarge,
                        color = DarkText,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "晴朗",
                        style = MaterialTheme.typography.labelMedium,
                        color = LightText
                    )
                }
            }
            
            // Weather details
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                WeatherDetailItem(
                    icon = Icons.Default.WaterDrop,
                    label = "湿度",
                    value = "45%"
                )
                WeatherDetailItem(
                    icon = Icons.Default.Cloud,
                    label = "风速",
                    value = "3级"
                )
            }
        }
    }
}

@Composable
private fun WeatherDetailItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = DeepOrangeLight,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.labelMedium,
            color = DarkText,
            fontWeight = FontWeight.Medium
        )
    }
}