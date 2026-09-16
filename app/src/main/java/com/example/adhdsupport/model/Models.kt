package com.example.adhdsupport.model

enum class Role {
    PARENT,
    KID
}

data class User(
    val id: String,
    val name: String,
    val email: String,
    val role: Role
)

data class Task(
    val id: String,
    val title: String,
    val description: String,
    val dueDate: String,
    val rewardPoints: Int,
    val isCompleted: Boolean = false
)

data class WordItem(
    val id: String,
    val englishWord: String,
    val vietnameseMeaning: String,
    val phonetic: String,
    val exampleSentence: String,
    val imageUrl: String = "" // In demo, we'll use placeholder resources or emojis
)

data class KidProgress(
    val tasksCompletedToday: Int,
    val totalTasksToday: Int,
    val wordsLearnedThisWeek: Int,
    val dailyStreak: Int,
    val totalPoints: Int
)
