package com.example.mymoneyandroid.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// CORES PADRÃO
private val CorFundoVerde = Color(0xFF2E7D32)
private val CorBotaoCinza = Color(0xFF3A3A3C)
private val CorTextoBranco = Color(0xFFFFFFFF)
private val CorCardGrafico = Color(0xFFFFFFFF)
private val verdeGradiente = Color(0xFF1A2E1A)

// LISTA GLOBAL: Definida fora da função para não resetar ao navegar
val listaCategorias = mutableStateListOf(
    "Saúde", "Lazer", "Casa", "Alimentação", "Educação", "Presentes",
    "Compras", "Família", "Exercícios", "Transporte", "Criar"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriaScreen(controleNavegacao: NavController) {

    var mostrarDialogo by remember { mutableStateOf(false) }
    var novoNome by remember { mutableStateOf("") }

    MenuScreen (tituloDaPagina = "", controleNagegacao = controleNavegacao) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = linearGradient(
                    colors = listOf(CorFundoVerde, verdeGradiente)
                    )
                )
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // Grid de Categorias
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.height(400.dp)
            ) {
                items(listaCategorias) { categoria ->
                    BotaoCategoria(nome = categoria) {
                        if (categoria == "Criar") {
                            mostrarDialogo = true
                        } else {
                            // Navega para a rota detalhecategoria/nomeCategoria
                            controleNavegacao.navigate("detalhescategoria/$categoria")
                        }
                    }
                }
            }

            // Gráfico com os nomes das duas primeiras categorias da lista
            CardGraficoCategorias(
                nome1 = listaCategorias.getOrNull(0) ?: " ",
                nome2 = listaCategorias.getOrNull(1) ?: " "
            )
        }
    }

    // Modal para criar nova categoria
    if (mostrarDialogo) {
        AlertDialog(
            onDismissRequest = { mostrarDialogo = false },
            containerColor = Color(0xFF1C1C1E),
            title = { Text("Nova Categoria", color = CorTextoBranco) },
            text = {
                OutlinedTextField(
                    value = novoNome,
                    onValueChange = { novoNome = it },
                    label = { Text("Nome da Categoria") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = CorTextoBranco,
                        unfocusedTextColor = CorTextoBranco,
                        focusedBorderColor = CorFundoVerde
                    )
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    if (novoNome.isNotEmpty()) {
                        // Adiciona antes do botão "Criar"
                        listaCategorias.add(listaCategorias.size - 1, novoNome)
                        novoNome = ""
                        mostrarDialogo = false
                    }
                }) { Text("Criar", color = CorFundoVerde) }
            }
        )
    }
}

@Composable
private fun CardGraficoCategorias(nome1: String, nome2: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CorCardGrafico),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Distribuição de Gastos", fontWeight = FontWeight.Bold, color = Color.Black)

            Spacer(modifier = Modifier.height(16.dp))

            // Gráfico Circular
            GraficoPizza(
                modifier = Modifier.size(160.dp),
                camposDoGrafico = listOf(
                    DividirGrafico(0.45f, Color(0xFFE8693A)), // Laranja
                    DividirGrafico(0.35f, Color(0xFF4A90D9)), // Azul
                    DividirGrafico(0.20f, Color(0xFF8E8E93))  // Cinza
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Legendas com nomes reais das categorias
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                LegendaItem(Color(0xFFE8693A), nome1)
                LegendaItem(Color(0xFF4A90D9), nome2)
                LegendaItem(Color(0xFF8E8E93), "Outras")
            }
        }
    }
}

@Composable
private fun BotaoCategoria(nome: String, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        color = CorBotaoCinza,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.height(55.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = nome, color = CorTextoBranco, fontSize = 13.sp, textAlign = TextAlign.Center)
        }
    }
}

@Composable
private fun LegendaItem(cor: Color, texto: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Canvas(modifier = Modifier.size(8.dp)) { drawCircle(color = cor) }
        Text(text = " $texto", fontSize = 11.sp, color = Color.DarkGray)
    }
}


// Lógica do Gráfico de Pizza
@Composable
private fun GraficoPizza(modifier: Modifier = Modifier, camposDoGrafico: List<DividirGrafico>) {
    Canvas(modifier = modifier) {
        var anguloInicial = -90f
        camposDoGrafico.forEach { campo ->
            val anguloOcupado = campo.angulo * 360f
            drawArc(
                color = campo.cor,
                startAngle = anguloInicial,
                sweepAngle = anguloOcupado,
                useCenter = true,
                size = Size(size.width, size.height)
            )
            anguloInicial += anguloOcupado
        }
    }
}