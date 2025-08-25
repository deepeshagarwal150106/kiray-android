package com.example.kiray.ui.myHouse

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kiray.common.AppErrorMsg
import com.example.kiray.common.LocalRole
import com.example.kiray.model.House
import com.example.kiray.ui.myHouse.viewmodel.MyHouseViewModel
import com.example.kiray.ui.structure.LoadingScreen
import com.example.kiray.ui.utils.isNetworkAvailable
import com.example.kiray.ui.utils.showToast
import com.google.android.gms.maps.model.LatLng

@Composable
fun MyHouseScreen(
    onEdit: (House) -> Unit,
    onClick: (House) -> Unit,
    onAddHouseClick: () -> Unit,
    viewModel: MyHouseViewModel = hiltViewModel()
) {
    val isOwner = LocalRole.current
    val uiState by viewModel.houseUiState.collectAsState()
    val context = LocalContext.current
    // Trigger API when screen loads
    LaunchedEffect(Unit) {
        if (isNetworkAvailable(context)) {
            viewModel.getHouseList()
        } else {
            showToast(context, AppErrorMsg.NO_INTERNET_MSG)
        }
    }
    when {
        uiState.loading -> {
            LoadingScreen()
        }

        uiState.error != null -> {
            // Text("Error: ${uiState.error}") // TODO: ADD THIS AND REMOVE BELOW
            Scaffold(
                bottomBar = {
                    if (isOwner) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Button(
                                onClick = onAddHouseClick,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(text = "Add House")
                            }
                        }
                    }
                }
            ) { innerPadding ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.padding(16.dp)
                    ) {
                        updateUI(houseList(), onEdit, onClick, viewModel)
                    }
                }
            }
        }

        else -> {
            Scaffold(
                bottomBar = {
                    if (isOwner) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Button(
                                onClick = onAddHouseClick,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(text = "Add House")
                            }
                        }
                    }
                }
            ) { innerPadding ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.padding(16.dp)
                    ) {
                        updateUI(uiState.houses, onEdit, onClick, viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun updateUI(
    houses: List<House>?,
    onEdit: (House) -> Unit,
    onClick: (House) -> Unit,
    viewModel: MyHouseViewModel
) {
    houses?.forEach {
        ListedHouse(house = it, onEdit = { data ->
            onEdit(data)
        }, onDelete = { house ->
            viewModel.deleteHouse(house.id)
        }, onClick = { house ->
            onClick(house)
        })
    }
}

private fun houseList() =
    listOf(
        House(
            id = "1",
            name = "2 BHK Apartment",
            address = "123 MG Road, Bangalore",
            city = "Bangalore",
            price = "18000",
            rooms = "2 BHK",
            amenities = listOf("Wi-Fi", "Parking", "AC"),
            latLng = LatLng(12.9716, 77.5946)
        ),
        House(
            id = "2",
            name = "Cozy 1 BHK",
            address = "45 JP Nagar, Bangalore",
            city = "Bangalore",
            price = "12000",
            rooms = "1 BHK",
            amenities = listOf("Balcony", "Power Backup"),
            latLng = LatLng(12.9716, 77.5946)
        )
    )
