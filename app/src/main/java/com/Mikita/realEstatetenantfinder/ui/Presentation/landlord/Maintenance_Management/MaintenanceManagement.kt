package com.Mikita.realEstatetenantfinder.ui.Presentation.landlord.Maintenance_Management


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

data class MaintenanceRequest(
    val id: String,
    val tenantName: String,
    val description: String,
    val status: String
)

@HiltViewModel
class LandlordMaintenanceViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                requests = listOf(
                    MaintenanceRequest("1", "John Doe", "Leaky faucet", "Open"),
                    MaintenanceRequest("2", "Jane Smith", "Broken window", "In Progress")
                )
            )
        }
    }

    fun updateStatus(requestId: String) {
        _uiState.update { currentState ->
            val updatedRequests = currentState.requests.map { request ->
                if (request.id == requestId) {
                    val newStatus = when (request.status) {
                        "Open" -> "In Progress"
                        "In Progress" -> "Closed"
                        "Closed" -> "Open"
                        else -> request.status
                    }
                    request.copy(status = newStatus)
                } else {
                    request
                }
            }
            currentState.copy(requests = updatedRequests)
        }
    }
}

data class UiState(
    val requests: List<MaintenanceRequest> = emptyList()
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandlordMaintenanceScreen(
    navController: NavController,
    viewModel: LandlordMaintenanceViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value
    val requests = uiState.requests
    val showDialog = remember { mutableStateOf(false) }
    val selectedRequest = remember { mutableStateOf<MaintenanceRequest?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Maintenance Requests") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            items(requests) { request ->
                MaintenanceRequestCard(
                    request = request,
                    onCardClick = {
                        selectedRequest.value = request
                        showDialog.value = true
                    },
                    onStatusUpdate = { viewModel.updateStatus(request.id) }
                )
            }
        }
    }

    if (showDialog.value && selectedRequest.value != null) {
        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
                selectedRequest.value = null
            },
            title = { Text("Request Details") },
            text = {
                Column {
                    Text("Tenant: ${selectedRequest.value?.tenantName}")
                    Text("Issue: ${selectedRequest.value?.description}")
                    Text("Status: ${selectedRequest.value?.status}")
                }
            },
            confirmButton = {
                Button(onClick = { showDialog.value = false }) {
                    Text("Close")
                }
            }
        )
    }
}

@Composable
fun MaintenanceRequestCard(
    request: MaintenanceRequest,
    onCardClick: () -> Unit,
    onStatusUpdate: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onCardClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Tenant: ${request.tenantName}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                "Issue: ${request.description}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Status: ${request.status}",
                    style = MaterialTheme.typography.bodySmall,
                    color = when (request.status) {
                        "Open" -> MaterialTheme.colorScheme.error
                        "In Progress" -> MaterialTheme.colorScheme.primary
                        "Closed" -> MaterialTheme.colorScheme.secondary
                        else -> MaterialTheme.colorScheme.onSurface
                    }
                )
                IconButton(onClick = onStatusUpdate) {
                    Icon(
                        Icons.Default.Refresh,
                        contentDescription = "Update Status",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun LandlordMaintenanceScreenPrev() {
    val navController = NavController(context = LocalContext.current)
    LandlordMaintenanceScreen(navController = navController)


}
