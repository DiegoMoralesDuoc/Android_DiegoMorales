package com.duoc.diegomorales.ui.register

import com.duoc.diegomorales.data.Manager
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onBackToLogin: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }

    Column (modifier = Modifier.fillMaxSize().padding(15.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text ("Registrarse", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            label = {Text("Correo")},
            modifier = Modifier.fillMaxWidth(),
            placeholder = {Text("Ingresa tu Correo")}
        )

        Spacer( modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {password = it},
            label = {Text("Contraseña")},
            modifier = Modifier.fillMaxWidth(),
            placeholder = {Text("Ingresa tu Contraseña")},
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = icon,
                        contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                    )
                }
            }
        )

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {confirmPassword = it},
            label = {Text("Confirmar Contraseña")},
            modifier = Modifier.fillMaxWidth(),
            placeholder = {Text("Confirmar tu Contraseña")},
            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                    Icon(
                        imageVector = icon,
                        contentDescription = if (confirmPasswordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = {
                try {
                    if(password == confirmPassword) {
                        val success = Manager.registerUser( email,password)
                        if (success) {
                            message = "El registro ha sido Exitoso"
                            onRegisterSuccess()
                        } else {
                            message = "El Correo ya ha sido registrado"
                        }
                    } else {
                        message = "Las contraseñas ingresadas no coinciden"
                    }
                } catch (e: Exception) {
                message = "Ocurrió un error al registrar: ${e.message}"
                }
            }, modifier = Modifier.fillMaxWidth().semantics{
                contentDescription = "Botón para Registrarse"
            }
        ) {
            Text ("Registrarse")
        }

        Spacer (modifier = Modifier.height(10.dp))

        Button(
            onClick = onBackToLogin,
            modifier = Modifier.fillMaxWidth().semantics{
                contentDescription = "Botón para volver al Inicio de Sesión"
            }
        ) {
            Text("Volver al Inicio de Sesión")
        }

        if (message.isNotEmpty()){
            Spacer (modifier = Modifier.height(10.dp))
            Text(message, color = MaterialTheme.colorScheme.primary)
        }
    }
}