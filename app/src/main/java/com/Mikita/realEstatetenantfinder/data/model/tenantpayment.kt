package com.Mikita.realEstatetenantfinder.data.model


data class Payment(
    val id: String,
    val amount: Double,
    val status: String, // "Paid", "Pending", etc.
    val date: String // e.g., "Apr 2025"
)
