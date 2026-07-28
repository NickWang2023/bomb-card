package com.bombcard.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bombcard.app.ui.theme.*
import com.bombcard.app.utils.LunarCalendar

@Composable
fun DateHeader() {
    val currentDate = LunarCalendar.getCurrentDate()
    val lunarDate = LunarCalendar.getLunarDate()
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Main date card
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = DeepOrange.copy(alpha = 0.1f),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = "日期",
                    tint = DeepOrange,
                    modifier = Modifier.size(24.dp)
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = currentDate,
                        style = MaterialTheme.typography.titleMedium,
                        color = DarkText,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    Spacer(modifier = Modifier.height(2.dp))
                    
                    Text(
                        text = lunarDate,
                        style = MaterialTheme.typography.labelMedium,
                        color = LightText
                    )
                }
            }
        }
    }
}