package com.Mikita.realEstatetenantfinder.ui.Presentation.tenant.application

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.Mikita.realEstatetenantfinder.data.repository.ApplicationViewModel
import com.Mikita.realEstatetenantfinder.ui.Presentation.shared.ApplicationCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationScreen(
    navController: NavHostController,
    viewModel: ApplicationViewModel
) {
    val backgroundColor = Color(0xFF1A1A1A)
    val accentColor = Color(0xFF00C4B4)
    val surfaceColor = Color(0xFF2A2A2A)
    val applications by viewModel.applications.collectAsState()
    var showApplyDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My Applications",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("tenant_dashboard_screen") }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor,
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showApplyDialog = true },
                containerColor = accentColor,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Apply for Property")
            }
        },
        containerColor = backgroundColor
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(backgroundColor, Color(0xFF2E2E2E))
                    )
                )
        ) {
            if (applications.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No Applications Yet",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                    Text(
                        text = "Tap the + button to apply for a property.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.7f),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(applications) { application ->
                        AnimatedApplicationCard(
                            application = application,
                            cardColor = surfaceColor,
                            textColor = Color.White,
                            onClick = {
                                // Placeholder for details navigation
                                // navController.navigate("application_details/${application.id}")
                            }
                        )
                    }
                }
            }
        }
    }

    if (showApplyDialog) {
        ApplyForPropertyDialog(
            onDismiss = { showApplyDialog = false },
            onSubmit = { propertyName, landlordName ->
                viewModel.submitApplication(propertyName, landlordName)
                showApplyDialog = false
            }
        )
    }
}

@Composable
fun AnimatedApplicationCard(
    application: com.Mikita.realEstatetenantfinder.domain.model.Application,
    cardColor: Color,
    textColor: Color,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(durationMillis = 200), label = ""
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clickable {
                isPressed = true
                onClick()
                isPressed = false
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        )
    ) {
        ApplicationCard(application = application)
    }
}

@Composable
fun ApplyForPropertyDialog(
    onDismiss: () -> Unit,
    onSubmit: (String, String) -> Unit
) {
    var propertyName by remember { mutableStateOf("") }
    var landlordName by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Apply for Property") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Property Name")
                BasicTextField(
                    value = propertyName,
                    onValueChange = { propertyName = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                        .padding(8.dp)
                )
                Text("Landlord Name")
                BasicTextField(
                    value = landlordName,
                    onValueChange = { landlordName = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                        .padding(8.dp)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (propertyName.isNotBlank() && landlordName.isNotBlank()) {
                        onSubmit(propertyName, landlordName)
                    }
                }
            ) {
                Text("Submit")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun ApplicationScreenPreview() {
    ApplicationScreen(
        navController = rememberNavController(),
        viewModel = ApplicationViewModel()
    )
}