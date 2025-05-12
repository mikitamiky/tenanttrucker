package com.Mikita.realEstatetenantfinder.ui.Presentation.landlord

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.Mikita.realEstatetenantfinder.data.model.navigation.LANDLORD_APPLICATION_SCREEN
import com.Mikita.realEstatetenantfinder.data.model.navigation.LANDLORD_CHAT_SCREEN
import com.Mikita.realEstatetenantfinder.data.model.navigation.LANDLORD_PAYMENT_SCREEN
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox

import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home

import androidx.compose.material.icons.filled.Person

@Composable
fun LandlordPackageScreen(navController: NavController) {
    val backgroundColor = Color(0xFF1A1A1A)
    val accentColor = Color(0xFF00C4B4)
    val surfaceColor = Color(0xFF2A2A2A)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(backgroundColor, Color(0xFF2E2E2E))
                )
            )
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Landlord Package Dashboard",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    listOf(
                        ActionItem(
                            title = "Home Dashboard",
                            icon = Icons.Default.Home,
                            destination = "landlord_home_screen"
                        ),
                        ActionItem(
                            title = "Manage Applications",
                            icon = Icons.Default.Person,
                            destination = LANDLORD_APPLICATION_SCREEN
                        ),
                        ActionItem(
                            title = "Chat with Tenants",
                            icon = Icons.Default.Email,
                            destination = LANDLORD_CHAT_SCREEN
                        ),
                        ActionItem(
                            title = "Manage Listings",
                            icon = Icons.Default.Home,
                            destination = "manage_listings_screen"
                        ),
                        ActionItem(
                            title = "Payment Dashboard",
                            icon = Icons.Default.AccountBox,
                            destination = LANDLORD_PAYMENT_SCREEN
                        )
                    )
                ) { item ->
                    PackageActionCard(
                        title = item.title,
                        icon = item.icon,
                        destination = item.destination,
                        navController = navController,
                        cardColor = surfaceColor,
                        textColor = Color.White,
                        iconTint = accentColor
                    )
                }
            }
        }
    }
}

data class ActionItem(val title: String, val icon: ImageVector, val destination: String)

@Composable
fun PackageActionCard(
    title: String,
    icon: ImageVector,
    destination: String,
    navController: NavController,
    cardColor: Color,
    textColor: Color,
    iconTint: Color
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(durationMillis = 200), label = ""
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .scale(scale)
            .clickable {
                isPressed = true
                navController.navigate(destination)
                isPressed = false
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "$title Icon",
                tint = iconTint,
                modifier = Modifier.size(32.dp)
            )
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = textColor,
                modifier = Modifier.padding(top = 8.dp),
                maxLines = 2,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LandlordPackageScreenPreview() {
    LandlordPackageScreen(navController = rememberNavController())
}