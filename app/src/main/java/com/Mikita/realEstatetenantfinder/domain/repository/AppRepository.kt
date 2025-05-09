package com.Mikita.realEstatetenantfinder.domain.repository

import com.Mikita.realEstatetenantfinder.domain.model.User


interface AppRepository {
    suspend fun loginUser(email: String, password: String): User?
    suspend fun getCurrentUser(): User?
    suspend fun logoutUser()
}