package com.example.mymoneyandroid.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mymoneyandroid.view.CadastroScreen
import com.example.mymoneyandroid.view.LoginScreen
import com.example.mymoneyandroid.view.CategoriaScreen
import com.example.mymoneyandroid.view.PrincipalScreen
import com.example.mymoneyandroid.view.DetalheCategoriaScreen
import com.example.mymoneyandroid.view.DetalheMetaScreen
import com.example.mymoneyandroid.view.InicialScreen
import com.example.mymoneyandroid.view.MensagemScreen
import com.example.mymoneyandroid.view.MetasScreen
import com.example.mymoneyandroid.view.PerfilScreen


@Composable
// Função central que vai ligar todas as telas do app
fun AppNavigation() {

    // Cria o controlador que gerencia a troca de telas
    val controleNavegacao = rememberNavController()

    // Cria o mapa, entrega para o controlador para começar pela telaInicial
    NavHost(navController = controleNavegacao, startDestination = "telaInicial") {

        // Rota da tela Inicial
        composable("telaInicial") {
            InicialScreen(
                controleNavegacao = controleNavegacao
            )
        }

        // Rota de Cadastro
        composable("telaCadastro") {
            CadastroScreen(
                controleNavegacao = controleNavegacao,
                irParaLogin = {
                    controleNavegacao.navigate("telaLogin")
                }
            )
        }

        // Rota de Login
        composable("telaLogin") {
            LoginScreen(
                controleNavegacao = controleNavegacao
            )
        }

        // Rota Mensagem
        composable(
            route ="telaMensagem/{idUsuario}",
            arguments = listOf(navArgument("idUsuario"){
                type = NavType.IntType
            })
        ) { tela ->
            val idUsuario = tela.arguments?.getInt("idUsuario") ?: 0
            MensagemScreen(
                controleNavegacao = controleNavegacao,
                idUsuario = idUsuario
            )
        }

        // Rota de Categorias
        composable(
            route ="telaCategoria/{idUsuario}",
            arguments = listOf(navArgument("idUsuario"){
                type = NavType.IntType
            })
        ) { tela ->
            val idUsuario = tela.arguments?.getInt("idUsuario") ?: 0
            CategoriaScreen(
                controleNavegacao = controleNavegacao,
                idUsuario = idUsuario
            )
        }

        // Rota da tela Principal
        composable(
            route ="telaPrincipal/{idUsuario}",
            arguments = listOf(navArgument("idUsuario"){
                type = NavType.IntType
            })
        ) { tela ->
            val idUsuario = tela.arguments?.getInt("idUsuario") ?: 0
            PrincipalScreen(
                controleNavegacao = controleNavegacao,
                idUsuario = idUsuario
            )
        }

        // Rota Metas
        composable(
            route ="telaMetas/{idUsuario}",
            arguments = listOf(navArgument("idUsuario"){
                type = NavType.IntType
            })
        ) { tela ->
            val idUsuario = tela.arguments?.getInt("idUsuario") ?: 0
            MetasScreen(
                controleNavegacao = controleNavegacao,
                idUsuario = idUsuario
            )
        }

        // Rota Perfil
        composable(
            route = "telaPerfil/{idUsuario}",
            arguments = listOf(navArgument("idUsuario"){
                type = NavType.IntType
            })
            ) { tela ->
            val idUsuario = tela.arguments?.getInt("idUsuario") ?: 0
            PerfilScreen(
                controleNavegacao = controleNavegacao,
                idUsuario = idUsuario
            )
        }

        // Rota para os detalhes de uma categoria
        composable(
            route = "detalhescategoria/{nomeCategoria}/{idUsuario}",
            arguments = listOf(
                navArgument("nomeCategoria") { type = NavType.StringType },
                navArgument("idUsuario") { type = NavType.IntType }
            )
        ) { tela ->
            val nome = tela.arguments?.getString("nomeCategoria")
            val idUsuario = tela.arguments?.getInt("idUsuario") ?: 0
            DetalheCategoriaScreen(
                controleNavegacao = controleNavegacao,
                nomeCategoria = nome,
                idUsuario = idUsuario)
        }

        // Rota para os detalhes de uma meta
        composable(
            route = "detalhemeta/{nomeMeta}/{idUsuario}",
            arguments = listOf(
                navArgument("nomeMeta") { type = NavType.StringType },
                navArgument("idUsuario") { type = NavType.IntType }
            )
        ) { tela ->
            val nome = tela.arguments?.getString("nomeMeta")
            val idUsuario = tela.arguments?.getInt("idUsuario") ?: 0
            DetalheMetaScreen(
                controleNavegacao = controleNavegacao,
                nomeMeta = nome,
                idUsuario = idUsuario)
        }
    }
}
