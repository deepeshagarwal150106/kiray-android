package com.example.kiray.ui.myHouse.tenent

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.kiray.model.House
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import androidx.core.net.toUri

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HouseDetailScreen(
    house: House?,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(house?.name?:"") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },

        bottomBar = {
            Button(
                onClick = {
                    val intent =
                        Intent(Intent.ACTION_DIAL).apply {
                            data = "tel:${house?.owner?.phone}".toUri()
                        }
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Phone, contentDescription = "Contact")
                Spacer(Modifier.width(8.dp))
                Text("Contact Owner")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Rent & Address
            item {
                Column {
                    Text(
                        text = "₹${house?.price}/month",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = house?.address?:"",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                    Text(
                        text = house?.city?:"",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }

            // Amenities
            item {
                Column {
                    Text("Amenities", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        house?.amenities?.forEach { amenity ->
                            AssistChip(
                                onClick = { },
                                label = { Text(amenity) }
                            )
                        }
                    }
                }
            }
            // Owner Details
            item {
                Column {
                    Text("Owner Details", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(2.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text("Name: ${house?.owner?.name}", style = MaterialTheme.typography.bodyLarge)
                            Spacer(Modifier.height(4.dp))
                            Text("Phone: ${house?.owner?.phone}", style = MaterialTheme.typography.bodyLarge)
                            Spacer(Modifier.height(4.dp))
                            Text("Email: ${house?.owner?.email}", style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
            }
            // Location on Map
            item {
                Column {
                    Text("Location", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                    ) {
                        GoogleMap(
                            modifier = Modifier.fillMaxSize(),
                            cameraPositionState = rememberCameraPositionState {
                                position =
                                    house?.latLng?.let { CameraPosition.fromLatLngZoom(it, 15f) }!!
                            }
                        ) {
                            val context = LocalContext.current
                            house?.latLng?.let { MarkerState(position = it) }?.let {
                                Marker(
                                    state = it,
                                    title = house?.name?:"",
                                    snippet = house.address,
                                    onClick = {
                                        val gmmIntentUri =
                                            Uri.parse("geo:${house.latLng.latitude},${house.latLng.longitude}?q=${Uri.encode(house.name)}")
                                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
                                            setPackage("com.google.android.apps.maps") // ensures Google Maps opens
                                        }
                                        context.startActivity(mapIntent)
                                        true // return true = consume click
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
