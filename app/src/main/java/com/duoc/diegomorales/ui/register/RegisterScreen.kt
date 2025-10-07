package com.duoc.diegomorales.ui.register

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.duoc.diegomorales.ui.viewmodel.UserViewModel

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onBackToLogin: () -> Unit,
    userViewModel: UserViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }

    val registerSuccess by userViewModel.registerSuccess.collectAsState()
    val isLoading by userViewModel.isLoading.collectAsState()

    // Manejar resultado del registro
    LaunchedEffect(registerSuccess) {
        when (registerSuccess) {
            true -> {
                message = "Registro exitoso"
                onRegisterSuccess()
                userViewModel.resetRegisterState()
            }
            false -> {
                message = "Error al registrar usuario"
                userViewModel.resetRegisterState()
            }
            null -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Registro de Usuario", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            placeholder = { Text("Ingresa tu Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo") },
            placeholder = { Text("Ingresa tu Correo") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            placeholder = { Text("Ingresa tu Contraseña") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = icon,
                        contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = {
                if (email.isBlank() || password.isBlank() || name.isBlank()) {
                    message = "Completa todos los campos"
                } else {
                    userViewModel.register(email, password, name)
                }
            },
            modifier = Modifier.fillMaxWidth().semantics {
                contentDescription = "Botón para Registrar Usuario"
            },
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Cargando...")
            } else {
                Text("Registrar")
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        TextButton(
            onClick = onBackToLogin,
            modifier = Modifier.fillMaxWidth().semantics { contentDescription = "Botón para volver al Login" }
        ) {
            Text("Volver al Login")
        }

        if (message.isNotEmpty()) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(message, color = MaterialTheme.colorScheme.primary)
        }
    }
}
