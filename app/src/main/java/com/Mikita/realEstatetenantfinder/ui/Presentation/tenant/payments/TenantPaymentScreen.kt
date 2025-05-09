package com.Mikita.realEstatetenantfinder.ui.Presentation.tenant.payments


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.Mikita.realEstatetenantfinder.data.repository.TenantPaymentViewModel


@Composable
fun TenantPaymentScreen(navController: NavController,
    viewModel: TenantPaymentViewModel = TenantPaymentViewModel()
) {
    val dueAmount by viewModel.dueAmount.collectAsState()
    val status by viewModel.paymentStatus.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "💳 Payment Dashboard", style = MaterialTheme.typography.headlineMedium)

        Text(text = "Rent Due: KSh${String.format("%.2f", dueAmount)}" , style = MaterialTheme.typography.titleLarge)

        Text(text = "Status: $status", style = MaterialTheme.typography.bodyLarge)

        Button(
            onClick = { viewModel.makePayment() },
            enabled = dueAmount > 0.0 && status != "Processing..."
        ) {
            Text("Pay Now")
        }
    }
}

@Preview
@Composable
private fun TenantPaymentScreen() {
    TenantPaymentScreen(rememberNavController())


}

