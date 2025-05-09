package com.Mikita.realEstatetenantfinder.ui.Presentation.login

import com.Mikita.realEstatetenantfinder.domain.model.User


sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    data class Success(val user: User) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}
