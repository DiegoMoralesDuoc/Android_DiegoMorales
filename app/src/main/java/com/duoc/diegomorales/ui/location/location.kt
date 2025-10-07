package com.duoc.diegomorales.ui.location

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

@SuppressLint("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun BuscarDispositivoScreen(
    onBack: () -> Unit
) {
    val context = LocalContext.current
    var locationText by remember { mutableStateOf("Obteniendo ubicación...") }
    val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    val locationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    LaunchedEffect(locationPermissionState.status) {
        val isGranted = when (locationPermissionState.status) {
            is PermissionStatus.Granted -> true
            else -> false
        }

        if (isGranted) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                locationText = location?.let { "Latitud: ${it.latitude}, Longitud: ${it.longitude}" }
                    ?: "No se pudo obtener la ubicación"
            }
        } else {
            locationPermissionState.launchPermissionRequest()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Buscar Dispositivo", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = locationText, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(30.dp))
        Button(onClick = onBack) {
            Text("Volver")
        }
    }
}
