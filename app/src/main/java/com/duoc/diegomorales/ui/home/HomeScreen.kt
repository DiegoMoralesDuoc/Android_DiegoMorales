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

@Composable
fun HomeScreen(){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Column(horizontalAlignment = Alignment.CenterHorizontally){

            Image (
                painter = painterResource(R.drawable.opendoor),
                contentDescription = "Bienvenida",
                modifier = Modifier.size(150.dp)
            )

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "Bienvenido al Home",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}