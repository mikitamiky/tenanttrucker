package com.Mikita.realEstatetenantfinder.ui.Presentation.tenant.Maintenance

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Face
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.compose.rememberNavController
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.io.use
import kotlin.let
import kotlin.text.isBlank

class MaintenanceViewModel : ViewModel() {
    var description by mutableStateOf(TextFieldValue(""))
    var imageUri by mutableStateOf<Uri?>(null)
    var hasCameraPermission by mutableStateOf(false)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaintenanceRequestScreen(
    navController: NavController,
    viewModel: MaintenanceViewModel = viewModel()
) {
    val context = LocalContext.current

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            viewModel.imageUri?.let { uri ->
                viewModel.imageUri = uri
            }
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.imageUri = uri
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        viewModel.hasCameraPermission = isGranted
        if (isGranted) {
            val uri = createImageUri(context)
            if (uri != null) {
                viewModel.imageUri = uri
                cameraLauncher.launch(uri)
            } else {
                Toast.makeText(context, "Failed to create image file", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Maintenance Request") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Describe the issue",
                style = MaterialTheme.typography.headlineSmall
            )

            OutlinedTextField(
                value = viewModel.description,
                onValueChange = { viewModel.description = it },
                label = { Text("Issue Description") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            viewModel.imageUri?.let { uri ->
                val bitmap = remember(uri) {
                    context.contentResolver.openInputStream(uri)?.use { stream ->
                        BitmapFactory.decodeStream(stream)
                    }?.let { bitmap ->
                        bitmap.asImageBitmap()
                    }
                }
                bitmap?.let {
                    Image(
                        bitmap = it,
                        contentDescription = "Selected issue image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        if (viewModel.hasCameraPermission) {
                            val uri = createImageUri(context)
                            if (uri != null) {
                                viewModel.imageUri = uri
                                cameraLauncher.launch(uri)
                            } else {
                                Toast.makeText(context, "Failed to create image file", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            permissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Face, contentDescription = "Camera")
                    Text("Take Photo", modifier = Modifier.padding(start = 8.dp))
                }

                Button(
                    onClick = { galleryLauncher.launch("image/*") },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.AccountBox, contentDescription = "Gallery")
                    Text("Pick Image", modifier = Modifier.padding(start = 8.dp))
                }
            }

            Button(
                onClick = {
                    if (viewModel.description.text.isBlank()) {
                        Toast.makeText(context, "Please enter a description", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    Toast.makeText(context, "Request submitted", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Submit")
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.hasCameraPermission = checkCameraPermission(context)
    }
}

private fun checkCameraPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED
}

@RequiresApi(Build.VERSION_CODES.FROYO)
private fun createImageUri(context: Context): Uri? {
    return try {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val imageFileName = "JPEG_${timeStamp}_"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File.createTempFile(imageFileName, ".jpg", storageDir)
        FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            imageFile
        )
    } catch (e: Exception) {
        null
    }
}

@Preview
@Composable
private fun MaintenanceRequestScreenPreview() {
    val navController = rememberNavController()
    MaintenanceRequestScreen(navController)

}