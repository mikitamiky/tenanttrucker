package com.Mikita.realEstatetenantfinder.ui.Presentation.tenant.chat



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.Mikita.realEstatetenantfinder.data.model.ChatMessage


@Composable
fun ChatBubble(message: ChatMessage) {
    val bubbleColor = if (message.isFromTenant) Color(0xFFDCF8C6) else Color.LightGray
    val alignment = if (message.isFromTenant) Arrangement.End else Arrangement.Start

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = alignment
    ) {
        Box(
            modifier = Modifier
                .background(bubbleColor, shape = MaterialTheme.shapes.medium)
                .padding(12.dp)
        ) {
            Text(message.text)
        }
    }
}
