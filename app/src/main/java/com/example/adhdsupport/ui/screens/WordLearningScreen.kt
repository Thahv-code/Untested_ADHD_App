package com.example.adhdsupport.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adhdsupport.viewmodel.KidViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordLearningScreen(
    viewModel: KidViewModel,
    onBack: () -> Unit
) {
    val words by viewModel.words.collectAsState()
    var currentIndex by remember { mutableIntStateOf(0) }
    
    val currentWord = words.getOrNull(currentIndex)
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Learn Words") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (currentWord != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Flashcard
                Card(
                    shape = RoundedCornerShape(32.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(currentWord.imageUrl, fontSize = 80.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(currentWord.englishWord, fontSize = 48.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                        Text(currentWord.phonetic, fontSize = 24.sp, color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f))
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(currentWord.vietnameseMeaning, fontSize = 32.sp, color = MaterialTheme.colorScheme.onPrimaryContainer)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(currentWord.exampleSentence, fontSize = 18.sp, color = MaterialTheme.colorScheme.onPrimaryContainer, modifier = Modifier.padding(horizontal = 24.dp))
                    }
                }
                
                Spacer(modifier = Modifier.height(48.dp))
                
                Button(
                    onClick = {
                        viewModel.learnWord(currentWord.id)
                        if (currentIndex < words.size - 1) {
                            currentIndex++
                        } else {
                            onBack()
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(80.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                ) {
                    Text("I Learned This! ⭐ Next ➡️", fontSize = 24.sp)
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("All words learned!")
            }
        }
    }
}
