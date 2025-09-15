package com.duoc.diegomorales.ui.ListAllUser

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import com.duoc.diegomorales.data.Manager
import com.duoc.diegomorales.data.isDuoc
import com.duoc.diegomorales.data.isGmail
import com.duoc.diegomorales.data.displayName
import com.duoc.diegomorales.ui.home.HomeButton

@Composable
fun ListAllUsersScreen(
    correoUsuario: String,
    onGoHome: () -> Unit
) {
    val usuariosRegistrados = Manager.getAllUsersEmails()
    val gmailUsuarios = usuariosRegistrados.filter { it.isGmail() }
    val duocUsuarios = usuariosRegistrados.filter { it.isDuoc() }

    Box(modifier = Modifier.fillMaxSize()) {
        HomeButton(onClick = onGoHome)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 60.dp, start = 16.dp, end = 16.dp)
        ) {
            Text(
                text = "Usuarios con Gmail",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4285F4)
            )
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(gmailUsuarios) { usuario ->
                    UserCard(usuario, Color(0xFF4285F4))
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
                    UserCard(usuario, Color(0xFF0A8F08))
                }
            }
        }
    }
}

@Composable
fun UserCard(email: String, color: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = email.displayName,
                style = MaterialTheme.typography.bodyLarge,
                color = color,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
