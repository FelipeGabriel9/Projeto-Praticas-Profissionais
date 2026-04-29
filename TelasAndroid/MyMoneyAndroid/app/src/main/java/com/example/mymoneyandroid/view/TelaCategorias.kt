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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val ColorBackground  = Color(0xFF2E7D32)   // verde escuro
private val ColorHeader      = Color(0xFF1C1C1E)   // card escuro
private val ColorButton   = Color(0xFF3A3A3C)   // borda dos campos
private val ColorTextWhite   = Color(0xFFFFFFFF)   // borda dos campos

@Composable
fun CategoriaScreen() {
    val categorias = listOf(
        "Saúde", "Lazer", "Casa",
        "Café", "Educação", "Presentes",
        "Compras", "Família", "Exercícios",
        "Transporte", "Criar"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(ColorHeader)
                .padding(bottom = 48.dp)
        ) {
            TopBarIcons()

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Categorias",
                color = ColorTextWhite,
                fontSize = 34.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(24.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(60.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(categorias) { categoria -> CategoriaButtonItem(name = categoria) }
        }
    }
}

@Composable
private fun CategoriaButtonItem(name: String) {
    Surface(
        onClick = { /*conectar com API*/ },
        color = ColorButton,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .height(65.dp)
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

@Composable
private fun TopBarIcons() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "⊞",
            color = ColorTextWhite,
            fontSize = 26.sp
        )

        Text(
            text = "≡",
            color = ColorTextWhite,
            fontSize = 30.sp
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 720)
@Composable
fun CategoriaScreenPreview() {
    CategoriaScreen()
}