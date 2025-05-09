package com.Mikita.realEstatetenantfinder.data.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TenantPaymentViewModel : ViewModel() {

    private val _dueAmount = MutableStateFlow(500.00)
    val dueAmount: StateFlow<Double> = _dueAmount

    private val _paymentStatus = MutableStateFlow<String>("Unpaid")
    val paymentStatus: StateFlow<String> = _paymentStatus

    fun makePayment() {
        viewModelScope.launch {
            _paymentStatus.value = "Processing..."
            delay(2000)
            _paymentStatus.value = "Paid"
            _dueAmount.value = 0.0
        }
    }
}