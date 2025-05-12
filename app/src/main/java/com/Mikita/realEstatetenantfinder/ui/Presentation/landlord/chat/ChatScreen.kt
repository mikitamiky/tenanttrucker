package com.Mikita.realEstatetenantfinder.ui.Presentation.landlord.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import com.Mikita.realEstatetenantfinder.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandlordChatScreen(navController: NavController) {
    var message by remember { mutableStateOf("") }
    var chatHistory by remember { mutableStateOf(listOf<Pair<String, Boolean>>()) }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Tenant Chat",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = message,
                    onValueChange = { message = it },
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(24.dp))
                        .background(MaterialTheme.colorScheme.background)
                        .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f), RoundedCornerShape(24.dp))
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    textStyle = TextStyle(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 16.sp
                    ),
                    decorationBox = { innerTextField ->
                        if (message.isEmpty()) {
                            Text(
                                text = "Type a message...",
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                                fontSize = 16.sp
                            )
                        }
                        innerTextField()
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = {
                        if (message.isNotBlank()) {
                            chatHistory = chatHistory + Pair(message, true)
                            message = ""
                            coroutineScope.launch {
                                kotlinx.coroutines.delay(1000)
                                chatHistory = chatHistory + Pair("Thanks for your message!", false)
                                listState.animateScrollToItem(chatHistory.size - 1)
                            }
                            coroutineScope.launch {
                                listState.animateScrollToItem(chatHistory.size - 1)
                            }
                        }
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_send),
                        contentDescription = "Send",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(chatHistory) { (chatMessage, isSentByUser) ->
                ChatBubble(
                    message = chatMessage,
                    isSentByUser = isSentByUser
                )
            }
        }
    }
}

@Composable
fun ChatBubble(message: String, isSentByUser: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = if (isSentByUser) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(
                    topStart = 12.dp,
                    topEnd = 12.dp,
                    bottomStart = if (isSentByUser) 12.dp else 0.dp,
                    bottomEnd = if (isSentByUser) 0.dp else 12.dp
                ))
                .background(
                    if (isSentByUser) MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)
                    else MaterialTheme.colorScheme.surfaceVariant
                )
                .padding(12.dp)
        ) {
            Text(
                text = message,
                color = if (isSentByUser) MaterialTheme.colorScheme.onPrimary
                else MaterialTheme.colorScheme.onSurface,
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LandlordChatScreenPreview() {
    MaterialTheme {
        LandlordChatScreen(rememberNavController())
    }
}