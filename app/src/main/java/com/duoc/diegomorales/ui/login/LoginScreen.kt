package com.duoc.diegomorales.ui.login

import android.graphics.Outline
import com.duoc.diegomorales.data.Manager
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateRegister: () -> Unit,
    onNavigateRecover: () -> Unit
) {
    var email by remember { mutableStateOf ("") }
    var password by remember { mutableStateOf ("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf ("") }

    Column( modifier = Modifier.fillMaxSize().padding(15.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally)
    {

        Text(
            text = "Inicio de Sesión",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.semantics {
                contentDescription = "Inicio de Sesión"
            }
        )

        Spacer( modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it},
            label = { Text("Correo")},
            singleLine = true,
            placeholder = {Text("Ingresa tu Correo")}
        )

        Spacer( modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it},
            label = { Text("Contraseña")},
            singleLine = true,
            placeholder = {Text("Ingresa tu Contraseña")},
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = icon, contentDescription = if (passwordVisible) "Ocultar" else "Mostrar")
                }
            }
        )

        Spacer( modifier = Modifier.height(24.dp))

        Button (onClick = {
            if (Manager.loginUser(email,password)){
                message = "Inicio de sesión Exitoso"
                onLoginSuccess()
            } else {
                message = "Correo o Contraseña incorrectos"
            }
        }, modifier = Modifier.fillMaxWidth().semantics{
            contentDescription = "Botón de Ingresar"
        }) {
            Text ("Ingresar")
        }

        Spacer( modifier = Modifier.height(15.dp))

        TextButton(onClick = onNavigateRegister, modifier = Modifier.semantics{
            contentDescription = "Botón de Registrarse"
        }) {
            Text("Registrarse")
        }

        TextButton(onClick = onNavigateRegister, modifier = Modifier.semantics{
            contentDescription = "Botón de Recuperar contraseña"
        }) {
            Text("Recuperar contraseña")
        }

        Spacer( modifier = Modifier.height(15.dp))

        Text(message)
    }
}
