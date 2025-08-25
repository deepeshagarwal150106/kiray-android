package com.example.kiray.ui.myHouse.owner

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@SuppressLint("UnrememberedMutableState")
@Composable
fun HouseLocationPicker(
    initialLatLng: LatLng? = null,
    onLocationPicked: (LatLng) -> Unit
) {
    var markerPosition by remember { mutableStateOf(initialLatLng) }
    val currentLocation = LatLng(26.898773, 75.74064)

    val cameraPosition = initialLatLng?.let {
        CameraPosition.fromLatLngZoom(it, 10f)
    } ?: CameraPosition.fromLatLngZoom(currentLocation, 10f) // Default: Delhi

    val cameraPositionState = rememberCameraPositionState {
      position=cameraPosition
    }

    Box(
        Modifier.fillMaxSize()
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapClick = { latLng ->
                markerPosition = latLng
                onLocationPicked(latLng) // send back to parent (EditHouseScreen)
            }
        ) {
            markerPosition?.let { MarkerState(position = it) }?.let {
                Marker(
                    state = it,
                    title = "House Location",
                    snippet = "This is where your house is located"
                )
            }
        }
    }
}
