package com.Mikita.realEstatetenantfinder.ui.Presentation.tenant.application


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.Mikita.realEstatetenantfinder.data.repository.ApplicationViewModel
import com.Mikita.realEstatetenantfinder.ui.Presentation.shared.ApplicationCard


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationScreen(


    viewModel: ApplicationViewModel = ApplicationViewModel()
) {
    val applications by viewModel.applications.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("My Applications") })
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            if (applications.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("You haven’t applied to any property yet.")
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(applications) { application ->
                        ApplicationCard(application)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ApplicationScreen() {
    ApplicationScreen(viewModel = ApplicationViewModel())

}