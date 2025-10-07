package com.duoc.diegomorales.ui.recover

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.duoc.diegomorales.ui.viewmodel.UserViewModel

@Composable
fun RecoverPasswordScreen(
    onBackToLogin: () -> Unit,
    onPasswordReset: () -> Unit,
    userViewModel: UserViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    val resetSuccess by userViewModel.resetSuccess.collectAsState()
    val isLoading by userViewModel.isLoading.collectAsState()

    // Efecto para manejar el resultado del envío de correo
    LaunchedEffect(resetSuccess) {
        when (resetSuccess) {
            true -> message = "Se ha enviado un correo para restablecer tu contraseña."
            false -> message = "Error al enviar correo de recuperación"
            null -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Recuperar Contraseña", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo") },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Ingresa tu Correo") }
        )

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = { userViewModel.resetPassword(email) },
            modifier = Modifier
                .fillMaxWidth()
                .semantics { contentDescription = "Botón para Recuperar Contraseña" },
            enabled = !isLoading
        ) {
            Text(if (isLoading) "Cargando..." else "Recuperar Contraseña")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = onBackToLogin,
            modifier = Modifier
                .fillMaxWidth()
                .semantics { contentDescription = "Botón para volver al inicio de sesión" }
        ) {
            Text("Volver al inicio de sesión")
        }

        if (message.isNotEmpty()) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(message, color = MaterialTheme.colorScheme.primary)
        }
    }
}
