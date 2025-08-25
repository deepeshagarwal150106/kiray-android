package com.example.kiray.ui.myHouse.owner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kiray.common.AppErrorMsg
import com.example.kiray.data.model.request.house.HouseRequest
import com.example.kiray.model.House
import com.example.kiray.ui.myHouse.viewmodel.MyHouseViewModel
import com.example.kiray.ui.utils.isNetworkAvailable
import com.example.kiray.ui.utils.showToast

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditHouseScreen(
    house: House? = null,
    onSave: (House?) -> Unit,
    onCancel: () -> Unit,
    viewModel: MyHouseViewModel = hiltViewModel()
) {
    var title by remember { mutableStateOf(house?.name) }
    var address by remember { mutableStateOf(house?.address) }
    var city by remember { mutableStateOf(house?.city) }
    var rooms by remember { mutableStateOf(house?.rooms) }
    var description by remember { mutableStateOf(house?.description) }
    var rent by remember { mutableStateOf(house?.price) }
    var amenities by remember { mutableStateOf(house?.amenities?.joinToString(", ")) }
    var selectedLocation by remember { mutableStateOf(house?.latLng) }

    val uiState by viewModel.saveUiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(uiState.success) {
        uiState.house?.let {
            // Navigate back or show success toast
            onSave(it)
        }
    }

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            // Show error message
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (house != null) "Edit House" else "Add House")},
                navigationIcon = {
                    IconButton(onClick = onCancel) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                OutlinedTextField(
                    value = title ?: "",
                    onValueChange = { title = it },
                    label = { Text("House Title") },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                OutlinedTextField(
                    value = address ?: "",
                    onValueChange = { address = it },
                    label = { Text("Address") },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                OutlinedTextField(
                    value = city ?: "",
                    onValueChange = { city = it },
                    label = { Text("City") },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                OutlinedTextField(
                    value = rooms ?: "",
                    onValueChange = { rooms = it },
                    label = { Text("Rooms (BHK)") },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                OutlinedTextField(
                    value = rent ?: "",
                    onValueChange = { rent = it },
                    label = { Text("Monthly Rent") },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                OutlinedTextField(
                    value = amenities ?: "",
                    onValueChange = { amenities = it },
                    shape = RoundedCornerShape(16.dp),
                    label = { Text("Amenities (comma separated)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                OutlinedTextField(
                    value = description ?: "",
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                Text("Pick House Location", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    HouseLocationPicker(
                        initialLatLng = selectedLocation,
                        onLocationPicked = { selectedLocation = it }
                    )
                }
            }
            item {
                Spacer(Modifier.height(20.dp))
                Button(
                    onClick = {
                        if (isNetworkAvailable(context)) {
                            val updatedHouse = HouseRequest(
                                id = house?.id, // null if new
                                name = title ?: "",
                                address = address ?: "",
                                city = city ?: "",
                                price = rent ?: "",
                                rooms = rooms ?: "",
                                description = description ?: "",
                                amenities = amenities?.split(",")?.map { it.trim() },
                                latLng = selectedLocation,
                                ownerId = "" //TODO: fetch owner id here
                            )
                            viewModel.saveHouse(isEdit = house != null, request = updatedHouse)
                        } else {
                            showToast(context, AppErrorMsg.NO_INTERNET_MSG)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(if (house != null) "Update" else "Add House")
                }
            }
        }
    }
}
