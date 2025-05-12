package com.Mikita.realEstatetenantfinder.ui.Presentation.shared

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.Mikita.realEstatetenantfinder.domain.model.Application

@Composable
fun ApplicationCard(application: Application) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = application.propertyName, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
               Text(text = "Landlord: ${application.landlordName}")
            Text(text = "Status: ${application.status.capitalize()}")
        }
    }
}
