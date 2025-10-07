package com.duoc.diegomorales.ui.ListAllUser

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.duoc.diegomorales.data.displayName
import com.duoc.diegomorales.data.isDuoc
import com.duoc.diegomorales.data.isGmail
import com.duoc.diegomorales.ui.home.HomeButton
import com.duoc.diegomorales.ui.viewmodel.UserViewModel

@Composable
fun ListAllUsersScreen(
    correoUsuario: String,
    onGoHome: () -> Unit,
    userViewModel: UserViewModel = viewModel()
) {
    val usuariosRegistrados by userViewModel.allUsers.collectAsState()
    val isLoading by userViewModel.isLoading.collectAsState()

    var userToDelete by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        userViewModel.loadAllUsers()
    }

    val gmailUsuarios = usuariosRegistrados.filter { it.isGmail() }
    val duocUsuarios = usuariosRegistrados.filter { it.isDuoc() }

    Box(modifier = Modifier.fillMaxSize()) {
        HomeButton(onClick = onGoHome)

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 60.dp, start = 16.dp, end = 16.dp)
            ) {
                if (usuariosRegistrados.isEmpty()) {
                    Text(
                        text = "No hay usuarios registrados",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                } else {
                    Text(
                        text = "Usuarios con Gmail",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4285F4)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(gmailUsuarios) { usuario ->
                            UserCard(
                                email = usuario,
                                color = Color(0xFF4285F4),
                                onDelete = { userToDelete = usuario }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Usuarios de Duoc",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0A8F08)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(duocUsuarios) { usuario ->
                            UserCard(
                                email = usuario,
                                color = Color(0xFF0A8F08),
                                onDelete = { userToDelete = usuario }
                            )
                        }
                    }
                }
            }
        }
    }

    if (userToDelete != null) {
        AlertDialog(
            onDismissRequest = { userToDelete = null },
            title = { Text("Confirmar eliminación") },
            text = { Text("¿Seguro que quieres eliminar a ${userToDelete!!.displayName}?") },
            confirmButton = {
                TextButton(onClick = {
                    userViewModel.deleteUser(userToDelete!!)
                    userToDelete = null
                }) {
                    Text("Eliminar", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { userToDelete = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun UserCard(email: String, color: Color, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = email.displayName,
                style = MaterialTheme.typography.bodyLarge,
                color = color,
                fontWeight = FontWeight.Medium
            )

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar usuario",
                    tint = Color.Red
                )
            }
        }
    }
}
