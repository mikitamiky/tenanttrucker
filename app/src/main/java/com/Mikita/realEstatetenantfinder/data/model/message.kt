package com.Mikita.realEstatetenantfinder.data.model


import java.util.UUID

data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    val isFromTenant: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)
