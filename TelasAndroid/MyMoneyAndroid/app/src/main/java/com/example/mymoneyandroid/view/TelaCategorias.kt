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
    // Chamamos o Menu que você, que já cria  o Header preto e o título "Categorias"
    AbaMenu(title = "Categorias", navController = navController) { paddingValues ->

        val categorias = listOf("Saúde", "Lazer", "Casa", "Café", "Educação", "Presentes", "Compras",
            "Família", "Exercícios", "Transporte", "Criar")

        // Esta Column preenche o espaço que sobra abaixo do Header
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(ColorBackground) // Aplica o verde no fundo
                .padding(paddingValues)      // Evita que o grid fique "atrás" do header
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(40.dp), // Espalha os botões na vertical
                modifier = Modifier.fillMaxSize()
            ) {
                items(categorias) { categoria ->
                    CategoriaButtonItem(name = categoria, onClick = {
                        navController.navigate("detalhe_categoria/$categoria")
                    } )
                }
            }
        }
    }
}

@Composable
private fun CategoriaButtonItem(name: String, onClick: () -> Unit) {
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
                text = name,
                color = ColorTextWhite,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }
    }
}

//@Composable
//private fun TopBar() {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 20.dp, vertical = 16.dp),
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//
//        Text(
//            text = "⊞",
//            color = corTexto,
//            fontSize = 24.sp,
//            fontWeight = FontWeight.Bold
//        )
//
//        Text(
//            text = "≡",
//            color = corTexto,
//            fontSize = 28.sp
//        )
//    }
//}