package com.bombcard.app.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bombcard.app.data.SoulCard
import com.bombcard.app.ui.theme.*

@Composable
fun BombCard(
    card: SoulCard,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isVisible by remember { mutableStateOf(true) }
    
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(300)) + 
                slideInVertically(animationSpec = tween(300)) { it / 2 },
        exit = fadeOut(animationSpec = tween(200)) +
               slideOutVertically(animationSpec = tween(200)) { it / 2 }
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
                .shadow(
                    elevation = 12.dp,
                    shape = RoundedCornerShape(24.dp),
                    spotColor = DeepOrange.copy(alpha = 0.3f)
                ),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = CardBackground
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                CardBackground,
                                SoftCream.copy(alpha = 0.5f)
                            )
                        )
                    )
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Tags
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    card.tags.forEach { tag ->
                        TagChip(tag = tag)
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Content
                Text(
                    text = card.content,
                    style = MaterialTheme.typography.bodyLarge,
                    color = DarkText,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Source
                if (card.source.isNotEmpty()) {
                    Text(
                        text = "—— ${card.source}",
                        style = MaterialTheme.typography.labelMedium,
                        color = LightText,
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                
                // Divider
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    color = WarmOrange.copy(alpha = 0.3f),
                    thickness = 1.dp
                )
                
                // Refresh Button
                Button(
                    onClick = {
                        isVisible = false
                        onRefresh()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DeepOrange
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 4.dp,
                        pressedElevation = 8.dp
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "换一张",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "换一张",
                        style = MaterialTheme.typography.labelLarge,
                        color = WarmWhite
                    )
                }
            }
        }
    }
    
    LaunchedEffect(card.id) {
        isVisible = true
    }
}

@Composable
fun TagChip(tag: String) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = TagBackground.copy(alpha = 0.6f),
        modifier = Modifier.height(28.dp)
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = tag,
                style = MaterialTheme.typography.labelMedium,
                color = TagText
            )
        }
    }
}