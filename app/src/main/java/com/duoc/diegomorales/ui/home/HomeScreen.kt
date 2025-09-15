package com.duoc.diegomorales.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.duoc.diegomorales.R
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button


@Composable
fun HomeButton(onClick: () -> Unit){
    IconButton(onClick = onClick, modifier = Modifier.padding(16.dp)) {
        Icon(imageVector = Icons.Default.Home, contentDescription = "Ir al Home")
    }
}
@Composable
fun HomeScreen(
    correoUsuario: String,
    onGoHome: () -> Unit,
    onViewUsers: () -> Unit)
{
    Box(modifier = Modifier.fillMaxSize()) {

        HomeButton(onClick = onGoHome)

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.opendoor),
                contentDescription = "Bienvenida",
                modifier = Modifier.size(150.dp)
            )

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "Bienvenido $correoUsuario",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(25.dp))

            Button(onClick = onViewUsers){
                Text("Ver Usuarios Ingresados")
            }
        }
    }
}