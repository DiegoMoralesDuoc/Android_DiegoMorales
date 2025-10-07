package com.duoc.diegomorales

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.duoc.diegomorales.ui.home.HomeScreen
import com.duoc.diegomorales.ui.login.LoginScreen
import com.duoc.diegomorales.ui.register.RegisterScreen
import com.duoc.diegomorales.ui.recover.RecoverPasswordScreen
import com.duoc.diegomorales.ui.ListAllUser.ListAllUsersScreen
import com.duoc.diegomorales.ui.location.BuscarDispositivoScreen

object Screen {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val RECOVER = "recover"
    const val HOME = "home/{correo}"
    const val LIST_USERS = "list_users/{correo}"
    const val BUSCAR_DISPOSITIVO = "buscar_dispositivo"
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        Log.d("Firebase", "Firebase inicializado sin errores")
        setContent {
            AppNavHost()
        }
    }
}

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.LOGIN) {

        composable(Screen.LOGIN) {
            LoginScreen(
                onLoginSuccess = { correo -> navController.navigate("home/$correo") },
                onNavigateRegister = { navController.navigate(Screen.REGISTER) },
                onNavigateRecover = { navController.navigate(Screen.RECOVER) }
            )
        }

        composable(Screen.REGISTER) {
            RegisterScreen(
                onRegisterSuccess = { navController.navigate(Screen.LOGIN) },
                onBackToLogin = { navController.navigate(Screen.LOGIN) }
            )
        }

        composable(Screen.RECOVER) {
            RecoverPasswordScreen(
                onPasswordReset = { navController.navigate(Screen.LOGIN) },
                onBackToLogin = { navController.navigate(Screen.LOGIN) }
            )
        }

        composable(Screen.HOME) { backStackEntry ->
            val correo = backStackEntry.arguments?.getString("correo") ?: "Usuario"
            HomeScreen(
                correoUsuario = correo,
                onGoHome = { navController.navigate("home/$correo") { popUpTo("home/$correo") { inclusive = true } } },
                onViewUsers = { navController.navigate("list_users/$correo") },
                onBuscarDispositivo = { navController.navigate(Screen.BUSCAR_DISPOSITIVO) }
            )
        }

        composable(Screen.LIST_USERS) { backStackEntry ->
            val correo = backStackEntry.arguments?.getString("correo") ?: "Usuario"
            ListAllUsersScreen(
                correoUsuario = correo,
                onGoHome = { navController.navigate("home/$correo") }
            )
        }

        composable(Screen.BUSCAR_DISPOSITIVO) {
            BuscarDispositivoScreen(onBack = { navController.navigateUp() })
        }
    }
}
