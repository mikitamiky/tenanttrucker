package com.example.eventflow.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.eventflow.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoleSelectionScreen(navController: NavController, authViewModel: AuthViewModel) {
    val context = LocalContext.current
    var selectedRole by remember { mutableStateOf<String?>(null) }
    val isLoading by authViewModel.isLoading.collectAsState()
    val errorMessage by authViewModel.errorMessage.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    // Custom color scheme (aligned with LoginScreen and SignUpScreen)
    val backgroundColor = Color(0xFF1A1A1A) // Dark background
    val accentColor = Color(0xFF00C4B4) // Teal accent
    val surfaceColor = Color(0xFF2A2A2A) // Slightly lighter surface

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(horizontal = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 48.dp, bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header Section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Name your role",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
                )
                Text(
                    text = "Select how you want to use the app",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }

            // Role Selection Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = CardDefaults.cardColors(containerColor = surfaceColor),
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = { selectedRole = "Tenant" },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedRole == "Tenant") accentColor else Color.Gray,
                            contentColor = Color.White
                        )
                    ) {

                        Text(
                            text = "Tenant",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )

                        Button(
                            onClick = { selectedRole = "Landlord" },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedRole == "Landlord") accentColor else Color.Gray,
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = "Landlord",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                            )


                            errorMessage?.let {
                                Text(
                                    text = it,
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }

                    // Confirm Button
                    Button(
                        onClick = {
                            selectedRole?.let { role ->
                                coroutineScope.launch {
                                    authViewModel.saveUserRole(role, navController, context)
                                }
                            }
                        },
                        enabled = !isLoading && selectedRole != null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = MaterialTheme.shapes.small,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = accentColor,
                            contentColor = Color.White
                        )
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        } else {
                            Text(
                                text = "Confirm",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }}}