package com.example.adhdsupport.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val DarkText = Color(0xFF1E2432)
val GreyText = Color(0xFF7A869A)
val PurpleIcon = Color(0xFF6B4EFF)
val PurpleIconBg = Color(0xFFF0EBFF)
val ParentIconBg = Color(0xFFF4F5F7)

@Composable
fun AuthScreen(
    onDemoParentClick: () -> Unit,
    onDemoKidClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(64.dp))
        
        // Logo and App Name
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "ADHD Support",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )
        }
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // Titles
        Text(
            text = "Let's grow together",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = DarkText,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Supportive routine & learning companion",
            fontSize = 16.sp,
            color = GreyText,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // Section Header
        Text(
            text = "CHOOSE YOUR SPACE",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = GreyText.copy(alpha = 0.7f),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(16.dp))
        
        // Parent Space Card
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onDemoParentClick() },
            shape = RoundedCornerShape(20.dp),
            color = Color.White,
            border = BorderStroke(1.5.dp, DarkText)
        ) {
            Row(
                modifier = Modifier.padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Parent Space",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkText
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Create tasks, review insights & reward progress",
                        fontSize = 14.sp,
                        color = GreyText,
                        lineHeight = 20.sp
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Kid Space Card
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onDemoKidClick() },
            shape = RoundedCornerShape(20.dp),
            color = Color.White,
            shadowElevation = 8.dp, // Subtle shadow instead of border
            tonalElevation = 4.dp
        ) {
            Row(
                modifier = Modifier.padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Maya's World 🌟",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkText
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "See my tasks, play word games & collect stars",
                        fontSize = 14.sp,
                        color = GreyText,
                        lineHeight = 20.sp
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Bottom footer text
        Text(
            text = "bloom supports families with ADHD using gentle guidance.",
            fontSize = 12.sp,
            color = GreyText.copy(alpha = 0.6f),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp)
        )
    }
}
