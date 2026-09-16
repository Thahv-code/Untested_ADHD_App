package com.example.adhdsupport.data

import com.example.adhdsupport.model.KidProgress
import com.example.adhdsupport.model.Role
import com.example.adhdsupport.model.Task
import com.example.adhdsupport.model.User
import com.example.adhdsupport.model.WordItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

object MockDataRepository {
    // Current User Session
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    // Tasks
    private val _tasks = MutableStateFlow<List<Task>>(
        listOf(
            Task("1", "Brush Teeth", "Remember to brush for 2 minutes", "Today", 10, false),
            Task("2", "Pack Backpack", "Put all books in for tomorrow", "Today", 15, false),
            Task("3", "Read a Book", "Read 10 pages of your favorite book", "Today", 20, true)
        )
    )
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    // Vocabulary
    private val _words = MutableStateFlow<List<WordItem>>(
        listOf(
            WordItem("1", "Apple", "Quả táo", "/ˈæp.əl/", "I eat an apple every day.", "🍎"),
            WordItem("2", "Cat", "Con mèo", "/kæt/", "The cat is sleeping.", "🐈"),
            WordItem("3", "Sun", "Mặt trời", "/sʌn/", "The sun is shining bright.", "☀️")
        )
    )
    val words: StateFlow<List<WordItem>> = _words.asStateFlow()
    
    private var _learnedWordsCount = MutableStateFlow(14) // Demo initial state
    val learnedWordsCount: StateFlow<Int> = _learnedWordsCount.asStateFlow()

    // Progress
    fun getKidProgress(): KidProgress {
        val todayTasks = _tasks.value
        val completed = todayTasks.count { it.isCompleted }
        val totalPoints = todayTasks.filter { it.isCompleted }.sumOf { it.rewardPoints }
        return KidProgress(
            tasksCompletedToday = completed,
            totalTasksToday = todayTasks.size,
            wordsLearnedThisWeek = _learnedWordsCount.value,
            dailyStreak = 5,
            totalPoints = totalPoints
        )
    }

    // Actions
    fun loginAsParent() {
        _currentUser.value = User("parent_1", "Jane Doe", "parent@example.com", Role.PARENT)
    }

    fun loginAsKid() {
        _currentUser.value = User("kid_1", "Timmy Doe", "kid@example.com", Role.KID)
    }

    fun logout() {
        _currentUser.value = null
    }

    fun toggleTask(taskId: String) {
        _tasks.update { currentList ->
            currentList.map { task ->
                if (task.id == taskId) task.copy(isCompleted = !task.isCompleted) else task
            }
        }
    }

    fun addTask(title: String, description: String, dueDate: String, rewardPoints: Int) {
        _tasks.update { currentList ->
            val newTask = Task(
                id = UUID.randomUUID().toString(),
                title = title,
                description = description,
                dueDate = dueDate,
                rewardPoints = rewardPoints
            )
            currentList + newTask
        }
    }
    
    fun learnWord(wordId: String) {
        // In a real app, track which word is learned. For demo, just increment.
        _learnedWordsCount.update { it + 1 }
    }
}
