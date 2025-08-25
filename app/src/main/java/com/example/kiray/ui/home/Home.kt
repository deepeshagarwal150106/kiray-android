package com.example.kiray.ui.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LocalTonalElevationEnabled
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import androidx.core.net.toUri
import com.example.kiray.ui.structure.Loader

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val currentLocation = LatLng(26.898773, 75.74064)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentLocation, 17f)
    }
    val markers = viewModel.markers
    Log.d("markers",markers.toString())
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        markers.forEach { marker ->
            val markerLatLng = LatLng(marker.latitude, marker.longitude)
            Marker(
                state = rememberMarkerState(position = markerLatLng),
                title = marker.house?.name,
                snippet = "address: ${marker.house?.address} phone: ${marker.house?.phone}",
                onInfoWindowClick = {
                    onClick(it.position.latitude, it.position.longitude, context)
                }
            )
        }
    }
}
fun onClick(latitude:Double,longitude:Double,context:Context){
    val uri = "geo:$latitude,$longitude?q=$latitude,$longitude(Label)".toUri()
    val intent = Intent(Intent.ACTION_VIEW, uri).apply {
        setPackage("com.google.android.apps.maps")
    }
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    } else {
        Toast.makeText(context, "Google Maps not installed", Toast.LENGTH_SHORT).show()
    }
}

