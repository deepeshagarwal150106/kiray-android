package com.example.kiray.ui.register

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kiray.common.AppErrorMsg
import com.example.kiray.common.TextViewSpan
import com.example.kiray.model.User
import com.example.kiray.ui.structure.Loader
import com.example.kiray.ui.utils.isNetworkAvailable
import com.example.kiray.ui.utils.isValidEmail
import com.example.kiray.ui.utils.showToast

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    toOtpScreen: (User) -> Unit,
    toLoginScreen: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by viewModel.error
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    val loading by viewModel.loading

    LaunchedEffect(Unit) {
        viewModel.success.collect {
            val user = User(
                name = name,
                email = email,
                phone = phone,
                password = password
            )
            toOtpScreen(user)
        }
    }

    Box {

        if (loading) {
            Loader(modifier = Modifier.align(Alignment.Center))
        }
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
                    // Logo / Icon
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Register Logo",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(64.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Create Account",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Sign up to get started",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Full name") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("Mobile number") },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
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

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text("Confirm password") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = PasswordVisualTransformation(),
                        shape = RoundedCornerShape(16.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
                    )


                    if (errorMessage.isNotBlank()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            when {

                                phone.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank() -> {
                                    errorMessage = "All fields are required"
                                }

                                phone.length < 10 || !phone.isDigitsOnly() -> {
                                    errorMessage = "Enter a valid phone number"
                                }

                                !isValidEmail(email) -> {
                                    errorMessage = "Email id is not in valid format"
                                }

                                password.length < 6 -> {
                                    errorMessage = "Password must be at least 6 characters long"
                                }

                                password != confirmPassword -> {
                                    errorMessage = "Passwords do not match"
                                }

                                else -> {
                                    errorMessage = ""
                                    val user = User(
                                        name = name,
                                        email = email,
                                        phone = phone,
                                        password = password
                                    )
                                    if (isNetworkAvailable(context)) {
                                        viewModel.requestOtp(user)
                                    } else {
                                        showToast(context, AppErrorMsg.NO_INTERNET_MSG)
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Register")
                    }

                    TextViewSpan(
                        primaryText = "Already have an account? ",
                        spanText = "Login",
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable {
                                toLoginScreen()
                            })
                }
            }
        }
    }
}