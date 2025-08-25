package com.example.kiray.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kiray.R
import com.example.kiray.common.AppErrorMsg
import com.example.kiray.common.animation.AnimatedComponent
import com.example.kiray.common.animation.Direction
import com.example.kiray.data.model.request.EditProfileRequest
import com.example.kiray.model.User
import com.example.kiray.ui.structure.LoadingScreen
import com.example.kiray.ui.utils.isNetworkAvailable
import com.example.kiray.ui.utils.isValidEmail
import com.example.kiray.ui.utils.showToast


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    user: User?,
    onBackPress: () -> Unit,
    onSaveClick: (user:User) -> Unit = { _-> },
    viewModel: ProfileViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf(user?.name) }
    var email by remember { mutableStateOf(user?.email) }
    var mobile by remember { mutableStateOf(user?.phone) }
    var address by remember { mutableStateOf(user?.address) }
    var errorMessage by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current

    val uiState by viewModel.editProfileUiState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(uiState.success) {
        uiState.success?.let { updatedUser ->
            onSaveClick(updatedUser)
        }
    }

    if (uiState.loading) {
        LoadingScreen()
    }

    uiState.error?.let { error ->
        showToast(context,error)
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Edit Profile") },
                navigationIcon = {
                    IconButton(onClick = onBackPress) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        AnimatedComponent(Direction.BOTTOM_TO_TOP) { offset ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF5F5F5))
                    .padding(innerPadding)
                    .then(
                        Direction.BOTTOM_TO_TOP.modifier(offset)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    shape = RoundedCornerShape(24.dp), // Rounded card
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(R.drawable.logo),
                            contentDescription = "app logo " + stringResource(R.string.app_name),
                            modifier = Modifier
                                .clip(RoundedCornerShape(32.dp))
                                .width(120.dp)
                                .height(80.dp)
                        )
                        Spacer(modifier = Modifier.height(24.dp))

                        OutlinedTextField(
                            value = name?:"",
                            onValueChange = { name = it },
                            label = { Text("Full name") },
                            maxLines = 1,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = mobile?:"",
                            onValueChange = {
                                if (it.length <= 10 && it.all { c -> c.isDigit() }) mobile = it
                            },
                            label = { Text("Mobile number") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(
                            value = email?:"",
                            onValueChange = { email = it },
                            label = { Text("Email ID") },
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = address?:"",
                            onValueChange = { address = it },
                            label = { Text("Address") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                            shape = RoundedCornerShape(16.dp),
                            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
                        )

                        if (errorMessage.isNotBlank()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(errorMessage, color = MaterialTheme.colorScheme.error)
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = {
                                errorMessage = when {
                                    name?.isBlank() == true || mobile?.length != 10 || email?.isBlank() == true -> {
                                        "All fields are required"
                                    }

                                    !isValidEmail(email?:"") -> {
                                        "Invalid email format"
                                    }

                                    mobile?.length != 10 -> {
                                        "Mobile number must be 10 digits"
                                    }

                                    else -> {
                                        if (isNetworkAvailable(context)){
                                            viewModel.editProfile(EditProfileRequest(
                                                name,
                                                email,mobile,address
                                            ))
                                        }else{
                                            showToast(context,AppErrorMsg.NO_INTERNET_MSG)
                                        }
                                        ""
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Text("Save")
                        }
                    }
                }
            }
        }
    }
}
