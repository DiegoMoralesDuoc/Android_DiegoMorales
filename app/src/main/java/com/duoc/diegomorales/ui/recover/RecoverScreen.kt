package com.duoc.diegomorales.ui.recover

import com.duoc.diegomorales.data.Manager
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun RecoverPasswordScreen(
    onPasswordReset: () -> Unit,
    onBackToLogin: () -> Unit
) {
    var email  by remember { mutableStateOf("")}
    var newPassword  by remember { mutableStateOf("")}
    var message  by remember { mutableStateOf("")}

    Column (modifier = Modifier.fillMaxSize().padding(15.dp),
        verticalArrangement = Arrangement.Center
    ){
        Text("Recuperar Contraseña", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {email=it},
            label = {Text("Correo")},
            modifier = Modifier.fillMaxWidth(),
            placeholder = {Text("Ingresa tu Correo")}
        )

        Spacer( modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = newPassword,
            onValueChange = {newPassword=it},
            label = {Text("Nueva Contraseña")},
            modifier = Modifier.fillMaxWidth(),
            placeholder = {Text("Ingresa tu nueva Contraseña")}
        )

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = {
                val success = Manager.resetPassword(email, newPassword)
                if (success) {
                    message = "La Contraseña ha sido Actualizada con Éxito"
                    onPasswordReset()
                } else {
                    message = "El Correo no coincide"
                }
            },

            modifier = Modifier.fillMaxWidth().semantics{
                contentDescription = "Botón para Cambiar Contraseña"
            }
        ) {
            Text("Cambiar Contraseña")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = onBackToLogin, modifier = Modifier.fillMaxWidth()
                .semantics{
                contentDescription = "Botón para volver al inicio de sesión"
            }
        ){
            Text("Volver al inicio de sesión")
        }

        if (message.isNotEmpty()) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(message, color = MaterialTheme.colorScheme.primary)
        }
    }
}