package com.example.kiray.ui.otp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kiray.common.AppErrorMsg
import com.example.kiray.data.api.SignUpRequest
import com.example.kiray.model.User
import com.example.kiray.ui.structure.LoadingScreen
import com.example.kiray.ui.utils.isNetworkAvailable
import com.example.kiray.ui.utils.showToast

@Composable
fun OtpVerificationScreen(
    viewModel: OtpScreenViewModel = hiltViewModel(),
    onOtpVerified: () -> Unit,
    user: User?
) {
    var otp by remember { mutableStateOf("") }
    var errorMessage by viewModel.error
    val loading by viewModel.loading

    LaunchedEffect(Unit) {
        viewModel.success.collect {
            onOtpVerified()
        }
    }

    if(loading){
        LoadingScreen()
    }
    else {



        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("OTP Verification", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text("An OTP has been sent to email", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = otp,
                onValueChange = {
                    if (it.length <= 6 && it.all { char -> char.isDigit() }) {
                        otp = it
                    }
                },
                label = { Text("Enter 6-digit OTP") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            )

            if (errorMessage.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(errorMessage, color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(24.dp))

            val context = LocalContext.current
            Button(
                onClick = {
                    if (otp.length < 6) {
                        errorMessage = "Please enter a valid 6-digit OTP"
                    } else {
                        errorMessage = ""
                        if (isNetworkAvailable(context)) {
                            viewModel.registerUser(SignUpRequest(user = user, otp = otp.toInt()))
                        }else{
                            showToast(context, AppErrorMsg.NO_INTERNET_MSG)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Verify")
            }

            Spacer(modifier = Modifier.height(16.dp))

        }
    }

}
