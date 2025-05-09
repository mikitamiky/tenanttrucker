package com.Mikita.realEstatetenantfinder.ui.Presentation.tenant.home

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.Mikita.realEstatetenantfinder.data.model.navigation.LANDLORD_SCREEN
import com.Mikita.realEstatetenantfinder.data.model.navigation.ROUTE_ROLE_SELECTION
import com.example.eventflow.viewmodel.AuthViewModel
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TenantHomeScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val context = LocalContext.current
    val backgroundColor = Color(0xFF1A1A1A)
    val accentColor = Color(0xFF00C4B4)
    val surfaceColor = Color(0xFF2A2A2A)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Tenant Dashboard",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                actions = {
                    IconButton(onClick = {
                        authViewModel.logout(navController, context)
                    }) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Logout",
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
        containerColor = backgroundColor
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Welcome, Tenant!",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            HomeActionCard(
                title = "Chat with Landlord",
                icon = Icons.Default.Email,
                iconTint = accentColor,
                destination = LANDLORD_SCREEN,
                navController = navController,
                cardColor = surfaceColor,
                textColor = Color.White
            )

            HomeActionCard(
                title = "Maintenance Request",
                icon = Icons.Default.Settings,
                iconTint = accentColor,
                destination = "maintenance_screen",
                navController = navController,
                cardColor = surfaceColor,
                textColor = Color.White
            )

            HomeActionCard(
                title = "View Profile",
                icon = Icons.Default.Person,
                iconTint = accentColor,
                destination = ROUTE_ROLE_SELECTION,
                navController = navController,
                cardColor = surfaceColor,
                textColor = Color.White
            )
        }
    }
}

@Composable
fun HomeActionCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    destination: String,
    navController: NavController,
    cardColor: Color,
    textColor: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable {
                navController.navigate(destination)
            }
            .animateContentSize(animationSpec = spring()),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "$title Icon",
                tint = iconTint,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = textColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TenantHomePreview() {
    TenantHomeScreen(
        navController = rememberNavController(),
        authViewModel = AuthViewModel()
    )
}