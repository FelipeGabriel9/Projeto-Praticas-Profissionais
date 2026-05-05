package com.example.mymoneyandroid.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

private val ColorBackground = Color(0xFF2E7D32)   // Verde
private val ColorButton = Color(0xFF3A3A3C)   // Cinza 
private val ColorTextWhite = Color(0xFFFFFFFF) // Branco

@Composable
fun CategoriaScreen(navController: NavController) {

    MenuScreen(tituloDaPagina = "Categorias", controleNagegacao = navController) { paddingValues ->

        val categorias = listOf("Saúde", "Lazer", "Casa", "Café", "Educação", "Presentes", "Compras",
            "Família", "Exercícios", "Transporte", "Criar")

        // Preenche o espaço que sobra abaixo do Header, aplicando a cor verde ao fundo
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(ColorBackground)
                .padding(paddingValues)
        ) {
            // Cria o 'agrupamento' dos botões
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(40.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(categorias) { categoria ->
                    BotaoCategoria(nome = categoria, onClick = {
                        navController.navigate("detalhe_categoria/$categoria")
                    } )
                }
            }
        }
    }
}

// Cria uma função reutilizável para acessar cada botão de categoria
@Composable
private fun BotaoCategoria(nome: String, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        color = ColorButton,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .height(55.dp)
            .fillMaxWidth()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(4.dp)
        ) {
            Text(
                text = nome,
                color = ColorTextWhite,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }
    }
}
