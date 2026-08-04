package com.bombcard.app.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bombcard.app.ui.theme.*

@Composable
fun WeatherWidget() {
    val context = LocalContext.current
    
    // 可配置的广告/推广区域
    Surface(
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        color = DeepOrange.copy(alpha = 0.08f),
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable {
                // 点击打开链接 - 修改这里替换为你的链接
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/NickWang2023/bomb-card"))
                context.startActivity(intent)
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // 左侧：图标和标题
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.OpenInBrowser,
                    contentDescription = "打开链接",
                    tint = DeepOrange,
                    modifier = Modifier.size(32.dp)
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column {
                    Text(
                        text = "💣 炸弹卡片",
                        style = MaterialTheme.typography.titleMedium,
                        color = DarkText,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "点击了解更多 · 每日心灵鸡汤",
                        style = MaterialTheme.typography.labelMedium,
                        color = LightText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            
            // 右侧：箭头或按钮
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = DeepOrange.copy(alpha = 0.15f),
                modifier = Modifier.height(36.dp)
            ) {
                Box(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "去看看",
                        style = MaterialTheme.typography.labelMedium,
                        color = DeepOrange,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
