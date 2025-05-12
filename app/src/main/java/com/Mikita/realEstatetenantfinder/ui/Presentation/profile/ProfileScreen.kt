package com.Mikita.realEstatetenantfinder.ui.Presentation.profile

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.PickVisualMediaRequest
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.eventflow.data.AuthRepository
import com.example.eventflow.viewmodel.AuthViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.InputStream
import java.io.IOException
import kotlin.let
import kotlin.text.equals
import kotlin.text.ifEmpty
import kotlin.text.isNotEmpty
import kotlin.text.substringBefore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, authViewModel: AuthViewModel) {
    val context = LocalContext.current
    val authRepository = AuthRepository()
    val user = authRepository.getCurrentUser()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var isEditMode by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf(user?.email ?: "") }
    var bio by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var profileImageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var deleteConfirmPassword by remember { mutableStateOf("") }

    // Image picker launcher
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let {
            try {
                val inputStream: InputStream? = context.contentResolver.openInputStream(it)
                profileImageBitmap = BitmapFactory.decodeStream(inputStream)
                inputStream?.close()
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Profile picture updated")
                }
            } catch (e: IOException) {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Failed to load image")
                }
            }
        }
    }

    LaunchedEffect(user) {
        user?.uid?.let { userId ->
            authRepository.getUserData(userId).collectLatest { userData ->
                name = userData?.name ?: ""
                bio = userData?.bio ?: ""
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(user?.email?.substringBefore("@") ?: "Profile") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                ),
                actions = {
                    if (isEditMode) {
                        IconButton(onClick = {
                            isEditMode = false
                            name = ""
                            email = user?.email ?: ""
                            bio = ""
                            newPassword = ""
                        }) {
                            Icon(Icons.Default.Close, contentDescription = "Cancel")
                        }
                        IconButton(onClick = {
                            coroutineScope.launch {
                                val result = authViewModel.updateUserProfile(
                                    displayName = name,
                                    email = email,
                                    bio = bio,
                                    password = if (newPassword.isNotEmpty()) newPassword else null
                                )
                                if (result.isSuccess) {
                                    snackbarHostState.showSnackbar("Profile updated successfully")
                                    isEditMode = false
                                    newPassword = ""
                                } else {
                                    snackbarHostState.showSnackbar(
                                        result.exceptionOrNull()?.message ?: "Update failed"
                                    )
                                }
                            }
                        }) {
                            Icon(Icons.Default.Check, contentDescription = "Save")
                        }
                    } else {
                        IconButton(onClick = { isEditMode = true }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit Profile")
                        }
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Picture
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                    .clickable(enabled = isEditMode) {
                        imagePicker.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                if (profileImageBitmap != null) {
                    Image(
                        bitmap = profileImageBitmap!!.asImageBitmap(),
                        contentDescription = "Profile Picture",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile Picture",
                        modifier = Modifier.size(50.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Name and Bio
            Text(
                text = name.ifEmpty { "Your Name" },
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = bio.ifEmpty { "Add a bio" },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp),
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Edit Profile Button
            if (!isEditMode) {
                OutlinedButton(
                    onClick = { isEditMode = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Edit Profile")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Profile Info Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (isEditMode) {
                        ProfileFieldItem(
                            icon = Icons.Default.Person,
                            label = "Name",
                            value = name,
                            onValueChange = { name = it },
                            isEditable = true
                        )
                        ProfileFieldItem(
                            icon = Icons.Default.Info,
                            label = "Bio",
                            value = bio,
                            onValueChange = { bio = it },
                            isEditable = true,
                            placeholder = "Tell us about yourself"
                        )
                        ProfileFieldItem(
                            icon = Icons.Default.Email,
                            label = "Email Address",
                            value = email,
                            onValueChange = { email = it },
                            isEditable = true
                        )
                        ProfileFieldItem(
                            icon = Icons.Default.Lock,
                            label = "New Password",
                            value = newPassword,
                            onValueChange = { newPassword = it },
                            isEditable = true,
                            isPassword = true,
                            placeholder = "Enter new password (optional)"
                        )
                    } else {
                        ProfileInfoItem(
                            icon = Icons.Default.Person,
                            label = "Name",
                            value = name.ifEmpty { "Not set" }
                        )
                        ProfileInfoItem(
                            icon = Icons.Default.Info,
                            label = "Bio",
                            value = bio.ifEmpty { "Not set" }
                        )
                        ProfileInfoItem(
                            icon = Icons.Default.Email,
                            label = "Email",
                            value = email
                        )
                        ProfileInfoItem(
                            icon = Icons.Default.Person,
                            label = "User ID",
                            value = user?.uid ?: "N/A"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Logout and Delete Buttons
            ElevatedButton(
                onClick = { authViewModel.logout(navController, context) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Logout")
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = { showDeleteDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Delete Account")
            }
        }
    }

    // Delete Account Dialog
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
                deleteConfirmPassword = ""
            },
            title = { Text("Delete Account") },
            text = {
                Column {
                    Text(
                        text = "Are you sure you want to delete your account? This action cannot be undone.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = deleteConfirmPassword,
                        onValueChange = { deleteConfirmPassword = it },
                        label = { Text("Confirm Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            val result = authViewModel.deleteUserAccount(deleteConfirmPassword)
                            if (result.isSuccess) {
                                snackbarHostState.showSnackbar("Account deleted successfully")
                                showDeleteDialog = false
                                navController.navigate("login") {
                                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                                }
                            } else {
                                snackbarHostState.showSnackbar(
                                    result.exceptionOrNull()?.message ?: "Delete failed"
                                )
                                showDeleteDialog = false
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Delete Account")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                    deleteConfirmPassword = ""
                }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun ProfileInfoItem(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun ProfileFieldItem(
    icon: ImageVector,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isEditable: Boolean,
    isPassword: Boolean = false,
    placeholder: String = ""
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        if (isEditable) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                label = { Text(label) },
                visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
                placeholder = { Text(placeholder) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = !label.equals("Bio", ignoreCase = true),
                maxLines = if (label.equals("Bio", ignoreCase = true)) 4 else 1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Text
                )
            )
        } else {
            Column {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = value.ifEmpty { "Not set" },
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Preview
@Composable
private fun ProfileScreenPrev() {
    val navController = rememberNavController()
    val authViewModel = AuthViewModel()
    ProfileScreen(navController, authViewModel)

}