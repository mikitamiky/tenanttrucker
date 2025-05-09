package com.Mikita.realEstatetenantfinder.ui.Presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import com.Mikita.realEstatetenantfinder.R // Correct import for drawable resources
import com.Mikita.realEstatetenantfinder.data.model.navigation.ROUTE_LOGIN

@Composable
fun SplashScreen(
    navController: NavHostController,

) {
    LaunchedEffect(Unit) {
        delay(2000) // 2-second splash delay
        navController.navigate(ROUTE_LOGIN) {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
        }

    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "App Logo",
                modifier = Modifier.size(100.dp),
                tint = Color.Blue
            )
            Image(
                painter = painterResource(R.drawable.pps),
                contentDescription = "home",
                modifier = Modifier.size(300.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("My App", style = MaterialTheme.typography.headlineMedium)
        }
    }
}

@Preview
@Composable
private fun splashScreenPrev() {
    SplashScreen(
        navController = rememberNavController()
)}
