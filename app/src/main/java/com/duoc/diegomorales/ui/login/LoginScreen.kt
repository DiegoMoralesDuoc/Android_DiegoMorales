package com.duoc.diegomorales.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.duoc.diegomorales.R
import com.duoc.diegomorales.ui.viewmodel.UserViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: (String) -> Unit,
    onNavigateRegister: () -> Unit,
    onNavigateRecover: () -> Unit,
    userViewModel: UserViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }

    val loginSuccess by userViewModel.loginSuccess.collectAsState()
    val isLoading by userViewModel.isLoading.collectAsState()

    // Reactivar cambios de login
    LaunchedEffect(loginSuccess) {
        when (loginSuccess) {
            true -> {
                message = "Inicio de sesión exitoso"
                onLoginSuccess(email)
                userViewModel.resetLoginState()
            }
            false -> {
                message = "Correo o contraseña incorrectos"
                userViewModel.resetLoginState()
            }
            null -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.loginicon),
            contentDescription = "Logo de la aplicación",
            modifier = Modifier.size(120.dp)
        )

        Text(
            text = "Inicio de Sesión",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.semantics { contentDescription = "Inicio de Sesión" }
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo") },
            singleLine = true,
            placeholder = { Text("Ingresa tu Correo") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Correo") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            singleLine = true,
            placeholder = { Text("Ingresa tu Contraseña") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = { Icon(Icons.Default.Key, contentDescription = "Contraseña") },
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

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { userViewModel.login(email, password) },
            modifier = Modifier
                .fillMaxWidth()
                .semantics { contentDescription = "Botón para iniciar sesión" },
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
                Text("Iniciar Sesión")
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(onClick = onNavigateRegister) {
                Text("Registrarse")
            }
            TextButton(onClick = onNavigateRecover) {
                Text("Recuperar contraseña")
            }
        }

        if (message.isNotEmpty()) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(message, color = MaterialTheme.colorScheme.primary)
        }
    }
}
