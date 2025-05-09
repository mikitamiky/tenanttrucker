package com.Mikita.realEstatetenantfinder.domain.Presentation.landlord.listings


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

data class PropertyListing(val id: Int, val title: String, val location: String, val price: String)

@Composable
fun ManageListingsScreen(
    navController: NavController,
    listings: List<PropertyListing> = remember {
        listOf(
            PropertyListing(1, "2-Bedroom Apartment", "Nairobi", "KSh 25,000"),
            PropertyListing(2, "Studio Apartment", "Mombasa", "KSh 15,000"),
            PropertyListing(3, "3-Bedroom House", "Kisumu", "KSh 40,000")
        )
    },
    onAddListing: () -> Unit,
    onEditListing: (PropertyListing) -> Unit,
    onDeleteListing: (PropertyListing) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddListing) {
                Text("+")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "Manage Listings",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn {
                items(listings) { listing ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(text = listing.title, style = MaterialTheme.typography.titleLarge)
                            Text(text = "Location: ${listing.location}", style = MaterialTheme.typography.bodyMedium)
                            Text(text = "Price: ${listing.price}", style = MaterialTheme.typography.bodyMedium)

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Button(onClick = { onEditListing(listing) }) {
                                    Text("Edit")
                                }
                                Button(onClick = { onDeleteListing(listing) }) {
                                    Text("Delete")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ManageListingsScreenPreview() {
    ManageListingsScreen(rememberNavController(),
        onAddListing = {},
        onEditListing = {},
        onDeleteListing = {}
    )
}