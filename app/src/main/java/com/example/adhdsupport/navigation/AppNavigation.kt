package com.example.adhdsupport.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.adhdsupport.model.Role
import com.example.adhdsupport.ui.screens.AuthScreen
import com.example.adhdsupport.ui.screens.KidDashboardScreen
import com.example.adhdsupport.ui.screens.ParentDashboardScreen
import com.example.adhdsupport.ui.screens.WordLearningScreen
import com.example.adhdsupport.viewmodel.AuthViewModel
import com.example.adhdsupport.viewmodel.KidViewModel
import com.example.adhdsupport.viewmodel.ParentViewModel

object Routes {
    const val AUTH = "auth"
    const val PARENT_DASHBOARD = "parent_dashboard"
    const val KID_DASHBOARD = "kid_dashboard"
    const val WORD_LEARNING = "word_learning"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    
    val authViewModel: AuthViewModel = viewModel()
    val currentUser by authViewModel.currentUser.collectAsState()
    
    val startDestination = if (currentUser == null) {
        Routes.AUTH
    } else {
        if (currentUser?.role == Role.PARENT) Routes.PARENT_DASHBOARD else Routes.KID_DASHBOARD
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Routes.AUTH) {
            AuthScreen(
                onDemoParentClick = { 
                    authViewModel.demoLoginParent()
                    navController.navigate(Routes.PARENT_DASHBOARD) {
                        popUpTo(Routes.AUTH) { inclusive = true }
                    }
                },
                onDemoKidClick = { 
                    authViewModel.demoLoginKid()
                    navController.navigate(Routes.KID_DASHBOARD) {
                        popUpTo(Routes.AUTH) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Routes.PARENT_DASHBOARD) {
            val parentViewModel: ParentViewModel = viewModel()
            ParentDashboardScreen(
                viewModel = parentViewModel,
                onLogout = {
                    authViewModel.logout()
                    navController.navigate(Routes.AUTH) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Routes.KID_DASHBOARD) {
            val kidViewModel: KidViewModel = viewModel()
            KidDashboardScreen(
                viewModel = kidViewModel,
                onLogout = {
                    authViewModel.logout()
                    navController.navigate(Routes.AUTH) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onNavigateToWordLearning = {
                    navController.navigate(Routes.WORD_LEARNING)
                }
            )
        }
        
        composable(Routes.WORD_LEARNING) {
            val kidViewModel: KidViewModel = viewModel()
            WordLearningScreen(
                viewModel = kidViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
