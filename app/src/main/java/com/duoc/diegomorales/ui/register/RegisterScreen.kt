package com.duoc.diegomorales.ui.register

import com.duoc.diegomorales.data.Manager
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onBackToLogin: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
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
            placeholder = {Text("Ingresa tu Contraseña")}
        )

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {confirmPassword = it},
            label = {Text("Confirmar Contraseña")},
            modifier = Modifier.fillMaxWidth(),
            placeholder = {Text("Confirmar tu Contraseña")}
        )

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = {
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