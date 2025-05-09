package com.example.eventflow.models

data class UserModel(
    val userId: String = "",
    val name: String = "",
    val email: String = "",
    val role: String = "" // Added role field
)