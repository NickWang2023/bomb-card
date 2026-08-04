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
import com.bombcard.app.utils.ConfigManager

@Composable
fun WeatherWidget() {
    val context = LocalContext.current
    
    // 从配置文件读取底部链接配置
    val config = remember { ConfigManager.getBottomLinkConfig(context) }
    
    // 如果禁用则不显示
    if (!config.enabled) {
        Spacer(modifier = Modifier.height(0.dp))
        return
    }
    
    Surface(
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        color = DeepOrange.copy(alpha = 0.08f),
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable {
                try {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(config.url))
                    context.startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
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
                        text = config.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = DarkText,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = config.subtitle,
                        style = MaterialTheme.typography.labelMedium,
                        color = LightText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            
            // 右侧：按钮
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
                        text = config.buttonText,
                        style = MaterialTheme.typography.labelMedium,
                        color = DeepOrange,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
