//package com.Mikita.realEstatetenantfinder.data
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.Mikita.realEstatetenantfinder.domain.usecase.LoginUserUseCase
//import com.Mikita.realEstatetenantfinder.ui.theme.Presentation.login.LoginUiState
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//
//class LoginViewModel(
//    private val loginUseCase: LoginUserUseCase
//) : ViewModel() {
//
//    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
//    val uiState: StateFlow<LoginUiState> = _uiState
//
//    fun login(email: String, password: String) {
//        viewModelScope.launch {
//            _uiState.value = LoginUiState.Loading
//            try {
//                val user = loginUseCase(email, password)
//                if (user != null) {
//                    _uiState.value = LoginUiState.Success(user)
//                } else {
//                    _uiState.value = LoginUiState.Error("Invalid credentials")
//                }
//            } catch (e: Exception) {
//                _uiState.value = LoginUiState.Error(e.localizedMessage ?: "Unknown error")
//            }
//        }
//    }
//}
