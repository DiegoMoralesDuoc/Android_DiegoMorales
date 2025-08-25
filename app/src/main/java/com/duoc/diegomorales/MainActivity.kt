package com.duoc.diegomorales

import android.os.Bundle
import android.view.Surface
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.duoc.diegomorales.ui.theme.DiegoMoralesTheme
import kotlin.random.Random
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ElevatedButton
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.duoc.diegomorales.ui.home.HomeScreen
import com.duoc.diegomorales.ui.login.LoginScreen
import com.duoc.diegomorales.ui.register.RegisterScreen
import com.duoc.diegomorales.ui.recover.RecoverPasswordScreen


object Screen {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val RECOVER = "recover"
    const val HOME = "home"
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavHost()
        }
    }
}

@Composable
fun AppNavHost(){
    val navController = rememberNavController()
    NavHost(navController = navController,startDestination = Screen.LOGIN){

        composable(Screen.LOGIN){
            LoginScreen(
                onLoginSuccess = {navController.navigate(Screen.HOME)},
                onNavigateRegister = {navController.navigate(Screen.REGISTER)},
                onNavigateRecover = {navController.navigate(Screen.RECOVER)}
            )
        }

        composable(Screen.HOME){HomeScreen()}

        composable(Screen.REGISTER){
            RegisterScreen(
                onRegisterSuccess = {navController.navigate(Screen.LOGIN)},
                onBackToLogin = {navController.navigate(Screen.LOGIN)}
            )
        }

        composable(Screen.RECOVER){
            RecoverPasswordScreen(
                onPasswordReset = {navController.navigate(Screen.LOGIN)},
                onBackToLogin = {navController.navigate(Screen.LOGIN)}
            )
        }
    }
}
