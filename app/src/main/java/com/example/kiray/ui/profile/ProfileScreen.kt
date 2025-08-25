package com.example.kiray.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kiray.common.AppErrorMsg
import com.example.kiray.common.animation.AnimatedComponent
import com.example.kiray.common.animation.Direction
import com.example.kiray.data.model.request.ProfileRequest
import com.example.kiray.model.User
import com.example.kiray.ui.structure.LoadingScreen
import com.example.kiray.ui.utils.isNetworkAvailable
import com.example.kiray.ui.utils.showToast


@Composable
fun ProfileScreen(
    name: String = "",
    email: String = "",
    phone: String = "",
    onEditProfile: (User) -> Unit,
    onLogOut: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.profileUiState.collectAsState()

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        if (isNetworkAvailable(context)) {
            viewModel.getProfile()
        } else {
            showToast(context, AppErrorMsg.NO_INTERNET_MSG)
        }
    }

    if (uiState.loading) {
        LoadingScreen()
    } else {
        AnimatedComponent(Direction.RIGHT_TO_LEFT) { offset ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF5F5F5))
                    .then(
                        Direction.RIGHT_TO_LEFT.modifier(offset)
                    ),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Profile Pic Placeholder
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                            .padding(16.dp),
                        tint = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = uiState.name ?: name,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = uiState.email ?: email,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )

                    Text(
                        text = uiState.phone ?: phone,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Edit Profile Button
                    Button(
                        onClick = {
                            onEditProfile.invoke(User(
                                name = uiState.name,
                                email = uiState.email,
                                address = uiState.address,
                                phone = uiState.phone
                            ))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Edit Profile")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Logout Button
                    OutlinedButton(
                        onClick = onLogOut,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.Black
                        )
                    ) {
                        Text("Logout")
                    }
                }
            }
        }
    }
}
