package com.example.mymoneyandroid.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mymoneyandroid.view.*

@Composable
fun AppNavigation() {
    val controleNavegacao = rememberNavController()

    NavHost(navController = controleNavegacao, startDestination = "telaInicial") {

        composable("telaInicial") { InicialScreen(controleNavegacao = controleNavegacao) }

        composable("telaCadastro") {
            CadastroScreen(controleNavegacao = controleNavegacao, irParaLogin = {
                controleNavegacao.navigate("telaLogin")
            })
        }

        composable("telaLogin") { LoginScreen(controleNavegacao = controleNavegacao) }

        composable(
            route = "telaMensagem/{idUsuario}",
            arguments = listOf(navArgument("idUsuario") { type = NavType.IntType })
        ) { tela ->
            val idUsuario = tela.arguments?.getInt("idUsuario") ?: 0
            MensagemScreen(controleNavegacao = controleNavegacao, idUsuario = idUsuario)
        }

        composable(
            route = "telaCategoria/{idUsuario}",
            arguments = listOf(navArgument("idUsuario") { type = NavType.IntType })
        ) { tela ->
            val idUsuario = tela.arguments?.getInt("idUsuario") ?: 0
            CategoriaScreen(controleNavegacao = controleNavegacao, idUsuario = idUsuario)
        }

        composable(
            route = "telaPrincipal/{idUsuario}",
            arguments = listOf(navArgument("idUsuario") { type = NavType.IntType })
        ) { tela ->
            val idUsuario = tela.arguments?.getInt("idUsuario") ?: 0
            PrincipalScreen(controleNavegacao = controleNavegacao, idUsuario = idUsuario)
        }

        composable(
            route = "telaMetas/{idUsuario}",
            arguments = listOf(navArgument("idUsuario") { type = NavType.IntType })
        ) { tela ->
            val idUsuario = tela.arguments?.getInt("idUsuario") ?: 0
            MetasScreen(controleNavegacao = controleNavegacao, idUsuario = idUsuario)
        }

        composable(
            route = "telaPerfil/{idUsuario}",
            arguments = listOf(navArgument("idUsuario") { type = NavType.IntType })
        ) { tela ->
            val idUsuario = tela.arguments?.getInt("idUsuario") ?: 0
            PerfilScreen(controleNavegacao = controleNavegacao, idUsuario = idUsuario)
        }

        composable(
            route = "detalhescategoria/{idCategoria}/{idUsuario}",
            arguments = listOf(
                navArgument("idCategoria") { type = NavType.IntType },
                navArgument("idUsuario") { type = NavType.IntType }
            )
        ) { tela ->
            val idCategoria = tela.arguments?.getInt("idCategoria") ?: 0
            val idUsuario = tela.arguments?.getInt("idUsuario") ?: 0
            DetalheCategoriaScreen(controleNavegacao = controleNavegacao, idCategoria = idCategoria, idUsuario = idUsuario)
        }

        composable(
            route = "detalhescategoriafixa/{nomeCategoria}/{idUsuario}",
            arguments = listOf(
                navArgument("nomeCategoria") { type = NavType.StringType },
                navArgument("idUsuario") { type = NavType.IntType }
            )
        ) { tela ->
            val nomeCategoria = tela.arguments?.getString("nomeCategoria") ?: ""
            val idUsuario = tela.arguments?.getInt("idUsuario") ?: 0
            DetalheCategoriaFixaScreen(controleNavegacao = controleNavegacao, nomeCategoria = nomeCategoria, idUsuario = idUsuario)
        }

        // Rota de Detalhes da Meta dinâmica vinda do banco
        // Rota ÚNICA para detalhes da meta
        composable(
            route = "detalhemeta/{idMeta}/{nomeMeta}/{idUsuario}",
            arguments = listOf(
                navArgument("idMeta") { type = NavType.IntType },
                navArgument("nomeMeta") { type = NavType.StringType },
                navArgument("idUsuario") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val idMeta = backStackEntry.arguments?.getInt("idMeta") ?: 0
            val nomeLogico = backStackEntry.arguments?.getString("nomeMeta")
            val nomeMeta = Uri.decode(nomeLogico) ?: "Meta"
            val idUsuario = backStackEntry.arguments?.getInt("idUsuario") ?: 0

            // Chama a nossa nova tela unificada!
            DetalheMetaScreen(
                controleNavegacao = controleNavegacao,
                idMeta = idMeta,
                nomeMeta = nomeMeta,
                idUsuario = idUsuario
            )
        }
    }
}