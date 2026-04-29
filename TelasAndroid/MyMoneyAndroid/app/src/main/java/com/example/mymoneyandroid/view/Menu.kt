package com.example.mymoneyandroid.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Cores padrão para o App
private val ColorHeader     = Color(0xFF222222)
private val ColorBackground = Color(0xFF1B823E)
private val ColorTextWhite  = Color(0xFFFFFFFF)
private val ColorMenu   = Color(0xFF1C1C1E)

@Composable
fun AbaMenu(title: String, navController: NavController, content: @Composable (PaddingValues) -> Unit)
{
    val estadoMenu = rememberDrawerState(initialValue = DrawerValue.Closed) // Começa fechado
    val controleMenu = rememberCoroutineScope() // Serve para funções assíncronas

    ModalNavigationDrawer(
        drawerState = estadoMenu,
        drawerContent = {
            TelaCampos(navController, estadoMenu, controleMenu)
        }
    ) {
        Scaffold(
            topBar = {
                // Header que vai se repetir em todas as telas
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(ColorHeader)
                        .padding(bottom = 20.dp)
                ) {
                    BarraSuperior(onMenuClick = { controleMenu.launch { estadoMenu.open() } })

                    Text(
                        text = title,
                        color = ColorTextWhite,
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            containerColor = ColorBackground // Fundo verde
        ) { paddingValues ->
            // Aqui é onde o conteúdo específico de cada tela aparece
            content(paddingValues)
        }
    }
}

@Composable
fun TelaCampos(navController: NavController, estadoMenu: DrawerState, controleMenu: CoroutineScope) {
    ModalDrawerSheet(
        drawerContainerColor = ColorMenu,
        drawerShape = RoundedCornerShape(0.dp),
        modifier = Modifier.width(280.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(24.dp)
        ) {
            Text("Menu", color = ColorTextWhite, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(40.dp))

            // Perfil
            Text(
                text = "Perfil",
                color = ColorTextWhite,
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .clickable {
                        controleMenu.launch { estadoMenu.close() } // Fecha a aba
                        navController.navigate("perfil") // Vai para a tela de perfil
                    }
            )

            // Tela Principal
            Text(
                text = "Principal",
                color = ColorTextWhite,
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .clickable {
                        controleMenu.launch { estadoMenu.close() }
                        navController.navigate("principal")
                    }
            )

            // Categorias
            Text(
                text = "Categorias",
                color = ColorTextWhite,
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .clickable {
                        controleMenu.launch { estadoMenu.close() }
                        navController.navigate("categorias")
                    }
            )

            Spacer(modifier = Modifier.weight(1f))

            // Mensagem
            Text(
                text = "Mensagem",
                color = ColorTextWhite,
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .clickable {
                        controleMenu.launch { estadoMenu.close() }
                        navController.navigate("mensagem")
                    }
            )
        }
    }
}

@Composable
private fun BarraSuperior(onMenuClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "≡",
            color = ColorTextWhite,
            fontSize = 30.sp,
            modifier = Modifier.clickable { onMenuClick() }
        )
    }
}