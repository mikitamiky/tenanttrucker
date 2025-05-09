package com.Mikita.realEstatetenantfinder.ui.Presentation.landlord.payment

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun LandlordPaymentScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Payment Dashboard",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            // Navigate to payment history screen
            navController.navigate("payment_history_screen")
        }) {
            Text("View Payment History")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            // Navigate to add payment method screen
            navController.navigate("add_payment_method_screen")
        }) {
            Text("Add Payment Method")
        }
    }
}

@Preview
@Composable
private fun LandlordPaymentScreenPreview() {
    LandlordPaymentScreen(rememberNavController())
}