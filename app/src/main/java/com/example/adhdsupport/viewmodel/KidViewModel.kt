package com.example.adhdsupport.viewmodel

import androidx.lifecycle.ViewModel
import com.example.adhdsupport.data.MockDataRepository
import com.example.adhdsupport.model.KidProgress
import com.example.adhdsupport.model.Task
import com.example.adhdsupport.model.WordItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class KidViewModel : ViewModel() {
    val tasks: StateFlow<List<Task>> = MockDataRepository.tasks
    val words: StateFlow<List<WordItem>> = MockDataRepository.words
    
    private val _progress = MutableStateFlow<KidProgress>(MockDataRepository.getKidProgress())
    val progress: StateFlow<KidProgress> = _progress.asStateFlow()
    
    fun updateProgress() {
        _progress.value = MockDataRepository.getKidProgress()
    }

    fun completeTask(taskId: String) {
        // Kid can only check off tasks, not uncheck in this simplified demo
        val task = tasks.value.find { it.id == taskId }
        if (task != null && !task.isCompleted) {
            MockDataRepository.toggleTask(taskId)
            updateProgress()
        }
    }
    
    fun learnWord(wordId: String) {
        MockDataRepository.learnWord(wordId)
        updateProgress()
    }
}
