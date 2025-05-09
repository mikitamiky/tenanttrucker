package com.Mikita.realEstatetenantfinder.ui.Presentation.landlord.application



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
fun LandlordApplicationScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Landlord Dashboard",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            // Navigate to property management screen
            navController.navigate("landlord_property_screen")
        }) {
            Text("Manage Properties")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            // Navigate to tenant applications screen
            navController.navigate("tenant_applications_screen")
        }) {
            Text("View Tenant Applications")
        }
    }
}

@Preview
@Composable
private fun LandlordApplicationScreenPreview() {
    LandlordApplicationScreen( rememberNavController())

}