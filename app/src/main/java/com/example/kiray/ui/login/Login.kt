package com.example.kiray.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kiray.R
import com.example.kiray.common.AppErrorMsg
import com.example.kiray.common.TextViewSpan
import com.example.kiray.data.api.SignInRequest
import com.example.kiray.ui.structure.LoadingScreen
import com.example.kiray.ui.utils.isNetworkAvailable
import com.example.kiray.ui.utils.isValidEmail
import com.example.kiray.ui.utils.showToast

@Composable
fun LoginScreen(
    onLogin: () -> Unit,
    toRegisterScreen: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by viewModel.error
    val loading by viewModel.loading

    LaunchedEffect(Unit) {
        viewModel.success.collect {
            onLogin()
        }
    }

    if (loading) {
        LoadingScreen()
    } else {
        val context = LocalContext.current
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5)), // Light gray background
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

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Welcome Back",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Login to continue",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email ID") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = PasswordVisualTransformation(),
                        shape = RoundedCornerShape(16.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
                    )

                    if (error.isNotBlank()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            when {
                                email.isBlank() || password.isBlank() -> {
                                    error = "All fields are required"
                                }

                                !isValidEmail(email) -> {
                                    error = "Email id is not in valid format"
                                }

                                password.length < 6 -> {
                                    error = "Password must be at least 6 characters long"
                                }

                                else -> {
                                    error = ""
                                    if (isNetworkAvailable(context)) {
                                        viewModel.loginUser(
                                            signInRequest = SignInRequest(
                                                email = email,
                                                password = password
                                            )
                                        )
                                    }else{
                                        showToast(context,AppErrorMsg.NO_INTERNET_MSG)
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Login")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    TextViewSpan(
                        primaryText = "Don't have an account? ",
                        spanText = "Click here",
                        modifier = Modifier.clickable {
                            toRegisterScreen()
                        })

                }
            }
        }
    }
}