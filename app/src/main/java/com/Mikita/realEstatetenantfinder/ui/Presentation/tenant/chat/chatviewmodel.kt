package com.Mikita.realEstatetenantfinder.ui.Presentation.tenant.chat



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Mikita.realEstatetenantfinder.data.model.ChatMessage

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TenantChatViewModel : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages

    fun sendMessage(text: String) {
        if (text.isBlank()) return

        val newMessage = ChatMessage(text = text, isFromTenant = true)
        _messages.value = _messages.value + newMessage

        // Simulated landlord response
        viewModelScope.launch {
            delay(1000)
            val reply = ChatMessage(text = "Thanks for your message!", isFromTenant = false)
            _messages.value = _messages.value + reply
        }
    }
}
