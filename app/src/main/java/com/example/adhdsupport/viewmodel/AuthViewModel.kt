package com.example.adhdsupport.viewmodel

import androidx.lifecycle.ViewModel
import com.example.adhdsupport.data.MockDataRepository
import com.example.adhdsupport.model.Role
import com.example.adhdsupport.model.User
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel : ViewModel() {
    val currentUser: StateFlow<User?> = MockDataRepository.currentUser
    
    fun demoLoginParent() {
        MockDataRepository.loginAsParent()
    }
    
    fun demoLoginKid() {
        MockDataRepository.loginAsKid()
    }
    
    fun logout() {
        MockDataRepository.logout()
    }
}
