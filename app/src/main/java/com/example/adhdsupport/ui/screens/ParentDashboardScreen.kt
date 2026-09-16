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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adhdsupport.model.KidProgress
import com.example.adhdsupport.model.Task
import com.example.adhdsupport.viewmodel.ParentViewModel

val BgColor = Color(0xFFF9FAFB)
val DarkBlueCard = Color(0xFF2C3E50)
val ProgressBarGreen = Color(0xFF10B981)
val ProgressBarBg = Color(0xFF455A64)
val LightPurpleBadgeBg = Color(0xFFF0EBFF)
val PurpleBadgeText = Color(0xFF6B4EFF)
val TextDark = Color(0xFF1E2432)
val TextGrey = Color(0xFF7A869A)
val TaskIconGreenBg = Color(0xFFE6F4EA)
val TaskIconGreen = Color(0xFF1E8E3E)
val TaskIconOrangeBg = Color(0xFFFEF3E6)
val TaskIconOrange = Color(0xFFF57C00)
val YellowStarBg = Color(0xFFFFF9E6)
val ApprovalOrangeText = Color(0xFFF68B59)
val ApprovalOrangeBg = Color(0xFFFFF4EC)
val ApproveGreenButton = Color(0xFF4ADE80)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParentDashboardScreen(
    viewModel: ParentViewModel,
    onLogout: () -> Unit
) {
    val tasks by viewModel.tasks.collectAsState()
    val progress by viewModel.progress.collectAsState()
    
    var isCreatingTask by remember { mutableStateOf(false) }
    var selectedTask by remember { mutableStateOf<Task?>(null) }

    Scaffold(
        containerColor = BgColor,
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                contentColor = TextGrey,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Rounded.Home, contentDescription = "Dashboard") },
                    label = { Text("Dashboard") },
                    selected = !isCreatingTask && selectedTask == null,
                    onClick = { 
                        isCreatingTask = false 
                        selectedTask = null
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = TextDark,
                        selectedTextColor = TextDark,
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Rounded.Add, contentDescription = "Create") },
                    label = { Text("Create") },
                    selected = isCreatingTask,
                    onClick = { 
                        isCreatingTask = true 
                        selectedTask = null
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = TextDark,
                        selectedTextColor = TextDark,
                        unselectedIconColor = TextGrey,
                        unselectedTextColor = TextGrey,
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Rounded.Info, contentDescription = "Insights") },
                    label = { Text("Insights") },
                    selected = false,
                    onClick = { },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = TextGrey,
                        unselectedTextColor = TextGrey
                    )
                )
            }
        }
    ) { paddingValues ->
        if (selectedTask != null) {
            ProgressDetailScreen(
                paddingValues = paddingValues,
                task = selectedTask!!,
                onBack = { selectedTask = null },
                onApprove = { 
                    viewModel.toggleTask(selectedTask!!.id)
                    selectedTask = null 
                }
            )
        } else if (isCreatingTask) {
            CreateTaskScreen(
                paddingValues = paddingValues,
                onBack = { isCreatingTask = false },
                onAssign = { title, desc, points ->
                    viewModel.addTask(title, desc, "Today", points)
                    isCreatingTask = false
                }
            )
        } else {
            DashboardContent(
                paddingValues = paddingValues,
                viewModel = viewModel,
                tasks = tasks,
                progress = progress,
                onCreateClick = { isCreatingTask = true },
                onTaskClick = { task -> selectedTask = task }
            )
        }
    }
}

@Composable
private fun DashboardContent(
    paddingValues: PaddingValues,
    viewModel: ParentViewModel,
    tasks: List<Task>,
    progress: KidProgress,
    onCreateClick: () -> Unit,
    onTaskClick: (Task) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        
        // Header Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column {
                Text(
                    text = "Hello, Parent",
                    color = TextGrey,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Maya's Day Overview",
                    color = TextDark,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            
            // Maya profile badge
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(LightPurpleBadgeBg)
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "M",
                    fontWeight = FontWeight.Bold,
                    color = PurpleBadgeText
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Maya",
                    color = PurpleBadgeText,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Today's Routine Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(DarkBlueCard)
                .padding(24.dp)
        ) {
            Column {
                Text(
                    text = "TODAY'S ROUTINE",
                    color = Color.White.copy(alpha = 0.6f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${progress.tasksCompletedToday} of ${progress.totalTasksToday} Done",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(modifier = Modifier.height(16.dp))
                
                val progressRatio = if (progress.totalTasksToday > 0) 
                    progress.tasksCompletedToday.toFloat() / progress.totalTasksToday 
                else 0f
                    
                LinearProgressIndicator(
                    progress = progressRatio,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = ProgressBarGreen,
                    trackColor = ProgressBarBg
                )
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Assigned Tasks Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Assigned Tasks",
                color = TextDark,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "+ Create New",
                color = PurpleBadgeText,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable { onCreateClick() }
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Task List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(tasks) { task ->
                ParentTaskItem(task = task, onClick = { onTaskClick(task) })
            }
        }
    }
}

@Composable
fun ParentTaskItem(task: Task, onClick: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        border = BorderStroke(1.dp, Color(0xFFF3F4F6)),
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon Background
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(if (task.isCompleted) TaskIconGreenBg else TaskIconOrangeBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (task.isCompleted) Icons.Rounded.Check else Icons.Rounded.Warning,
                    contentDescription = null,
                    tint = if (task.isCompleted) TaskIconGreen else TaskIconOrange,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Text Column
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = task.title,
                    color = TextDark,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (task.isCompleted) "Completed • Self-driven" else "Not started yet • Routine",
                    color = TextGrey,
                    fontSize = 12.sp
                )
            }
            
            // Reward Badge
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(YellowStarBg)
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "+${task.rewardPoints}",
                    color = TextDark,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "⭐",
                    fontSize = 10.sp,
                    modifier = Modifier.padding(start = 2.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CreateTaskScreen(
    paddingValues: PaddingValues,
    onBack: () -> Unit,
    onAssign: (title: String, desc: String, points: Int) -> Unit
) {
    var taskTitle by remember { mutableStateOf("Clean up playroom") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        
        // Top Bar
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.clickable { onBack() }.padding(8.dp),
                tint = TextDark
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Create New Task", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextDark)
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // WHAT'S THE TASK
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color.White,
            border = BorderStroke(1.dp, Color(0xFFF3F4F6)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("WHAT'S THE TASK?", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextGrey)
                Spacer(modifier = Modifier.height(16.dp))
                
                OutlinedTextField(
                    value = taskTitle,
                    onValueChange = { taskTitle = it },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFFF3F4F6),
                        focusedContainerColor = Color(0xFFF3F4F6),
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Chips
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SuggestionChip("🛏️ Make Bed")
                    SuggestionChip("🥦 Eat Veggies")
                    SuggestionChip("📚 Read 10 min")
                    SuggestionChip("👟 Put Shoes")
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // ADHD BREAKDOWN STEPS
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color.White,
            border = BorderStroke(1.dp, Color(0xFFF3F4F6)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("ADHD BREAKDOWN STEPS", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextGrey)
                    Text("+ Add Step", fontSize = 14.sp, color = PurpleBadgeText, fontWeight = FontWeight.Medium)
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                BreakdownStepItem("1", "Put building blocks in the blue bin", true)
                BreakdownStepItem("2", "Stack picture books on the shelf", false)
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // INCENTIVES & REWARDS
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color.White,
            border = BorderStroke(1.dp, Color(0xFFF3F4F6)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("INCENTIVES & REWARDS", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextGrey)
                Spacer(modifier = Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    RewardChip("⭐ 15 Points", YellowStarBg, TextDark)
                    RewardChip("\uD83C\uDF81 10m iPad time", TaskIconGreenBg, TaskIconGreen)
                }
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Assign Button
        Button(
            onClick = { onAssign(taskTitle, "ADHD breakdown applied", 15) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkBlueCard)
        ) {
            Text("Assign to Maya", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ProgressDetailScreen(
    paddingValues: PaddingValues,
    task: Task,
    onBack: () -> Unit,
    onApprove: () -> Unit
) {
    var parentNotes by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        
        // Top Bar
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.clickable { onBack() }.padding(8.dp),
                tint = TextDark
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Progress Detail", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextDark)
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Task Info Card
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color.White,
            border = BorderStroke(1.dp, Color(0xFFF3F4F6)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                // WAITING FOR APPROVAL Badge
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(ApprovalOrangeBg)
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                ) {
                    Text(
                        text = "WAITING FOR APPROVAL",
                        color = ApprovalOrangeText,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = task.title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextDark
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Completed by Maya at 4:32 PM today",
                    fontSize = 14.sp,
                    color = TextGrey
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text("STEP BREAKDOWN", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextGrey)
        Spacer(modifier = Modifier.height(12.dp))
        
        // Completed Step 1
        CompletedStepItem("Put building blocks in the blue bin")
        Spacer(modifier = Modifier.height(8.dp))
        // Completed Step 2
        CompletedStepItem("Stack picture books on the shelf")
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // PARENT NOTES
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color.White,
            border = BorderStroke(1.dp, Color(0xFFF3F4F6)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("PARENT NOTES", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextGrey)
                Spacer(modifier = Modifier.height(12.dp))
                
                OutlinedTextField(
                    value = parentNotes,
                    onValueChange = { parentNotes = it },
                    placeholder = { Text("Add notes or encouragement (optional)...", color = TextGrey, fontSize = 14.sp) },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFFF3F4F6),
                        focusedContainerColor = Color(0xFFF3F4F6),
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Approve Button
        Button(
            onClick = { onApprove() },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ApproveGreenButton)
        ) {
            Text("Approve & Grant Rewards \uD83C\uDF81", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Minor Correction Button
        OutlinedButton(
            onClick = { onBack() },
            modifier = Modifier.fillMaxWidth().height(56.dp).padding(bottom = 16.dp),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, Color(0xFFE5E7EB)),
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent)
        ) {
            Text("Ask for minor correction", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextGrey)
        }
    }
}

@Composable
fun CompletedStepItem(text: String) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        border = BorderStroke(1.dp, Color(0xFFF3F4F6)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.Check,
                contentDescription = "Completed",
                tint = ProgressBarGreen,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(text, fontSize = 14.sp, color = TextDark)
        }
    }
}

@Composable
fun SuggestionChip(text: String) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFFE5E7EB)),
        color = Color.White
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            color = TextDark,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun BreakdownStepItem(number: String, text: String, showDivider: Boolean) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(24.dp).clip(CircleShape).background(Color(0xFFF0F4F8)),
                contentAlignment = Alignment.Center
            ) {
                Text(number, fontSize = 12.sp, color = DarkBlueCard, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(text, fontSize = 14.sp, color = TextDark)
        }
        if (showDivider) {
            Divider(color = Color(0xFFF3F4F6), modifier = Modifier.padding(start = 36.dp))
        }
    }
}

@Composable
fun RewardChip(text: String, bgColor: Color, textColor: Color) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = bgColor
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            color = textColor,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}
