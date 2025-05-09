package com.Mikita.realEstatetenantfinder.data

import androidx.lifecycle.ViewModel
import com.Mikita.realEstatetenantfinder.domain.model.Application
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ApplicationViewModel : ViewModel() {

    private val _applications = MutableStateFlow<List<Application>>(emptyList())
    val applications: StateFlow<List<Application>> = _applications
    init {
        loadApplications()
    }

    private fun loadApplications() {
        // Simulated data fetch
        _applications.value = listOf(
            Application("1", "Green Villa", "pending", "Mr. Johnson"),
            Application("2", "Sunset Apartments", "approved", "Ms. Mikita")
        )
    }
}