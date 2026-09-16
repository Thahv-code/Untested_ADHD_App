package com.example.adhdsupport.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adhdsupport.model.Task
import com.example.adhdsupport.viewmodel.KidViewModel

val KidBgColor = Color(0xFFF9FAFB)
val KidTextDark = Color(0xFF1E2432)
val KidTextGrey = Color(0xFF7A869A)
val KidPurpleMain = Color(0xFF6B4EFF)
val KidPurpleLightCard = Color(0xFFEFEAFF)
val KidPurpleDarkText = Color(0xFF5338CC)
val KidYellowStarBg = Color(0xFFFFF9E6)
val KidOrangeCircleBg = Color(0xFFFFF0E6)
val KidOrangeText = Color(0xFFF57C00)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KidDashboardScreen(
    viewModel: KidViewModel,
    onLogout: () -> Unit,
    onNavigateToWordLearning: () -> Unit
) {
    val tasks by viewModel.tasks.collectAsState()
    val progress by viewModel.progress.collectAsState()
    
    var currentTab by remember { mutableStateOf("My Hub") }

    Scaffold(
        containerColor = KidBgColor,
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                contentColor = KidTextGrey,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Rounded.Home, contentDescription = "My Hub") },
                    label = { Text("My Hub") },
                    selected = currentTab == "My Hub",
                    onClick = { currentTab = "My Hub" },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = KidPurpleMain,
                        selectedTextColor = KidPurpleMain,
                        unselectedIconColor = KidTextGrey,
                        unselectedTextColor = KidTextGrey,
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Rounded.List, contentDescription = "Words") },
                    label = { Text("Words") },
                    selected = currentTab == "Words",
                    onClick = { 
                        currentTab = "Words"
                        onNavigateToWordLearning()
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = KidPurpleMain,
                        selectedTextColor = KidPurpleMain,
                        unselectedIconColor = KidTextGrey,
                        unselectedTextColor = KidTextGrey,
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Rounded.Check, contentDescription = "My Tasks") },
                    label = { Text("My Tasks") },
                    selected = currentTab == "My Tasks",
                    onClick = { currentTab = "My Tasks" },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = KidPurpleMain,
                        selectedTextColor = KidPurpleMain,
                        unselectedIconColor = KidTextGrey,
                        unselectedTextColor = KidTextGrey,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    ) { paddingValues ->
        if (currentTab == "My Hub") {
            KidHomeContent(
                paddingValues = paddingValues,
                totalPoints = progress.totalPoints,
                pendingTasksCount = tasks.count { !it.isCompleted },
                onStartQuest = onNavigateToWordLearning,
                onGoToTasks = { currentTab = "My Tasks" }
            )
        } else if (currentTab == "My Tasks") {
            KidTasksContent(
                paddingValues = paddingValues,
                tasks = tasks,
                onCheck = { viewModel.completeTask(it) }
            )
        }
    }
}

@Composable
fun KidHomeContent(
    paddingValues: PaddingValues,
    totalPoints: Int,
    pendingTasksCount: Int,
    onStartQuest: () -> Unit,
    onGoToTasks: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column {
                Text(
                    text = "Hi Maya! \uD83C\uDF1F",
                    color = KidPurpleMain,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Ready for some fun?",
                    color = KidTextGrey,
                    fontSize = 16.sp
                )
            }
            
            // Stars Badge
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(KidYellowStarBg)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "⭐", fontSize = 14.sp)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "$totalPoints Stars",
                    color = KidTextDark,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // AI Assistant Message
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            // Fox Avatar
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFFE0B2)),
                contentAlignment = Alignment.Center
            ) {
                Text("🦊", fontSize = 24.sp)
            }
            Spacer(modifier = Modifier.width(16.dp))
            
            // Chat Bubble
            Surface(
                shape = RoundedCornerShape(
                    topStart = 4.dp,
                    topEnd = 16.dp,
                    bottomStart = 16.dp,
                    bottomEnd = 16.dp
                ),
                color = Color.White,
                shadowElevation = 2.dp,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Hey Maya! You have $pendingTasksCount tasks left today. Complete them to get custom stickers!",
                    modifier = Modifier.padding(16.dp),
                    color = KidTextDark,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Daily Challenge Card
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = KidPurpleLightCard,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                // Tag
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color.White
                ) {
                    Text(
                        text = "DAILY CHALLENGE",
                        color = KidPurpleMain,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Learn Today's Word",
                    color = KidPurpleMain,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Unlock new vocabulary and win 5 bonus stars!",
                    color = KidTextDark,
                    fontSize = 14.sp
                )
                
                Spacer(modifier = Modifier.height(20.dp))
                
                Button(
                    onClick = onStartQuest,
                    modifier = Modifier.height(48.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = KidPurpleMain)
                ) {
                    Text("Start Quest \uD83D\uDE80", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
        
        Spacer(modifier = Modifier.height(20.dp))
        
        // My Tasks Checklist
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            border = BorderStroke(1.dp, Color(0xFFF3F4F6)),
            shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth().clickable { onGoToTasks() }
        ) {
            Row(
                modifier = Modifier.padding(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "My Tasks Checklist",
                        color = KidTextDark,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "$pendingTasksCount tasks waiting for you to finish",
                        color = KidTextGrey,
                        fontSize = 14.sp
                    )
                }
                
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(KidOrangeCircleBg),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = pendingTasksCount.toString(),
                        color = KidOrangeText,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
fun KidTasksContent(
    paddingValues: PaddingValues,
    tasks: List<Task>,
    onCheck: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "My Tasks Today",
            color = KidPurpleMain,
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Spacer(modifier = Modifier.height(24.dp))
        
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(tasks) { task ->
                KidTaskCard(task = task, onCheck = { onCheck(task.id) })
            }
        }
    }
}

@Composable
fun KidTaskCard(task: Task, onCheck: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.fillMaxWidth(),
        color = if (task.isCompleted) Color(0xFFF3F4F6) else Color.White,
        border = BorderStroke(1.dp, Color(0xFFE5E7EB)),
        shadowElevation = if (task.isCompleted) 0.dp else 2.dp
    ) {
        Row(
            modifier = Modifier.padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = task.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (task.isCompleted) KidTextGrey else KidTextDark
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "+${task.rewardPoints} ⭐",
                    fontSize = 16.sp,
                    color = if (task.isCompleted) KidTextGrey else KidOrangeText,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = { if (it) onCheck() },
                colors = CheckboxDefaults.colors(
                    checkedColor = KidPurpleMain,
                    uncheckedColor = KidTextGrey
                ),
                modifier = Modifier.scale(1.5f)
            )
        }
    }
}
