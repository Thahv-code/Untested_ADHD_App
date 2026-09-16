package com.example.adhdsupport.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adhdsupport.data.MockDataRepository
import com.example.adhdsupport.model.KidProgress
import com.example.adhdsupport.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn

class ParentViewModel : ViewModel() {
    val tasks: StateFlow<List<Task>> = MockDataRepository.tasks
    
    private val _progress = MutableStateFlow<KidProgress>(MockDataRepository.getKidProgress())
    val progress: StateFlow<KidProgress> = _progress.asStateFlow()
    
    init {
        updateProgress()
    }
    
    fun updateProgress() {
        _progress.value = MockDataRepository.getKidProgress()
    }

    fun toggleTask(taskId: String) {
        MockDataRepository.toggleTask(taskId)
        updateProgress()
    }
    
    fun addTask(title: String, description: String, dueDate: String, rewardPoints: Int) {
        MockDataRepository.addTask(title, description, dueDate, rewardPoints)
        updateProgress()
    }
}
