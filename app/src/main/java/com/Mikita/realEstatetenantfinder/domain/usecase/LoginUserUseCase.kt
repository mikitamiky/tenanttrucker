package com.Mikita.realEstatetenantfinder.domain.usecase


import com.Mikita.realEstatetenantfinder.domain.model.User
import com.Mikita.realEstatetenantfinder.domain.repository.AppRepository


class LoginUserUseCase(
    private val repository: AppRepository
) {
    suspend operator fun invoke(email: String, password: String): User? {

        return repository.loginUser(email, password)
    }
}
