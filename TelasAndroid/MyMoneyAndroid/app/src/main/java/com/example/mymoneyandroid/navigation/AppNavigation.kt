package com.example.mymoneyandroid.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mymoneyandroid.view.CadastroScreen
import com.example.mymoneyandroid.view.LoginScreen



@Composable
// Função central que vai ligar todas as telas do app
fun AppNavigation() {

    // Cria o controlador que gerencia o histórico e a troca de telas
    val navController = rememberNavController()

    // Cria o mapa, entrega para o controlador para começar pela tela1
    NavHost(navController = navController, startDestination = "cadastro") {

        // Cria o endereço/rota chamado exatamente "tela1"
        composable("cadastro") {

            // Chama o desenho da Tela 1
            CadastroScreen(
                onLoginClick = {
                    // O NavController faz a transição para a rota de login
                    navController.navigate("login")
                }
            )
        }

        // Rota de Login
        composable("login") {
            LoginScreen() // Sua tela de login

        }

    }
}