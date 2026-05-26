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

// Cores usadas na tela
private val CorHeader = Color(0xFF222222)
private val CorDeFundo = Color(0xFF1B823E)
private val CorTexto = Color(0xFFFFFFFF)
private val CorFundoMenu = Color(0xFF1C1C1E)

// Criando a função do menu
@Composable
fun MenuScreen(
    tituloDaPagina: String,
    controleNagegacao: NavController,
    idUsuario: Int,
    conteudoPagina: @Composable (PaddingValues) -> Unit)
{
    val estadoMenu = rememberDrawerState(initialValue = DrawerValue.Closed) // Controla se o menu está aberto ou fechado. Nesse caso, começa fechado
    val controleMenu = rememberCoroutineScope() // Serve para funções assíncronas

    // Permite abrir uma aba lateral
    ModalNavigationDrawer(
        drawerState = estadoMenu,
        drawerContent = {
            ConteudoMenu(controleNagegacao, estadoMenu, controleMenu, idUsuario)
        }
    ) {

        // Estrutura das telas
        Scaffold(
            topBar = {
                // Header que vai se repetir em todas as telas
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(CorHeader)
                ) {
                    // Chama a função que cria a barra superior com o ícone do menu
                    BarraSuperior(onMenuClick = { controleMenu.launch { estadoMenu.open() } })

                    // Título da página atual
                    if(tituloDaPagina.isNotEmpty())
                    {
                        Text(
                            text = tituloDaPagina,
                            color = CorTexto,
                            fontSize = 30.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp)
                        )
                    }
                }
            },
            containerColor = CorDeFundo // Cor de fundo verde
        ) { valoresPreenchimento -> // Aqui é onde o conteúdo específico de cada tela aparece
            conteudoPagina(valoresPreenchimento)
        }
    }
}

// Função que define o visual e os itens dentro do menu
@Composable
fun ConteudoMenu(
    controleNavegacao: NavController,
    estadoMenu: DrawerState,
    controleMenu: CoroutineScope,
    idUsuario: Int
) {
    ModalDrawerSheet(
        drawerContainerColor = CorFundoMenu,
        drawerShape = RoundedCornerShape(0.dp),
        modifier = Modifier.width(280.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(24.dp)
        ) {
            Text(
                text = "Menu",
                color = CorTexto,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(40.dp))

            // Perfil
            Text(
                text = "Perfil",
                color = CorTexto,
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .clickable {
                        controleMenu.launch { estadoMenu.close() }
                        controleNavegacao.navigate("telaPerfil/$idUsuario")
                    }
            )

            // Tela Principal
            Text(
                text = "Principal",
                color = CorTexto,
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .clickable {
                        controleMenu.launch { estadoMenu.close() }
                        controleNavegacao.navigate("telaPrincipal/$idUsuario")
                    }
            )

            // Categorias
            Text(
                text = "Categorias",
                color = CorTexto,
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .clickable {
                        controleMenu.launch { estadoMenu.close() }
                        controleNavegacao.navigate("telaCategoria/$idUsuario")
                    }
            )

            // Metas
            Text(
                text = "Metas",
                color = CorTexto,
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .clickable {
                        controleMenu.launch { estadoMenu.close() }
                        controleNavegacao.navigate("telaMetas/$idUsuario")
                    }
            )

            Spacer(modifier = Modifier.weight(1f)) // Joga o próximo texto para a parte de baixo da tela

            // Mensagem
            Text(
                text = "Mensagem",
                color = CorTexto,
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .clickable {
                        controleMenu.launch { estadoMenu.close() }
                        controleNavegacao.navigate("telaMensagem/$idUsuario")
                    }
            )
        }
    }
}

// Função usada nesta e em outras classes para criar a barra no topo da página
@Composable
fun BarraSuperior(onMenuClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "≡",
            color = CorTexto,
            fontSize = 30.sp,
            modifier = Modifier.clickable { onMenuClick() }
        )
    }
}