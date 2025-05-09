package com.Mikita.realEstatetenantfinder.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.Mikita.realEstatetenantfinder.data.model.navigation.ROUTE_LOGIN
import com.Mikita.realEstatetenantfinder.domain.model.User
import com.Mikita.realEstatetenantfinder.domain.repository.AppRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: AppRepository
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    init {
        loadUserProfile()
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            _isLoading.value = true
            _user.value = repository.getCurrentUser()
            _isLoading.value = false
        }
    }

    fun logout(navController: NavHostController) {
        viewModelScope.launch {
            mAuth.signOut() // Sign out from Firebase
            _user.value = null // Clear user data
            navController.navigate(ROUTE_LOGIN) // Navigate to the login screen
        }
    }
}