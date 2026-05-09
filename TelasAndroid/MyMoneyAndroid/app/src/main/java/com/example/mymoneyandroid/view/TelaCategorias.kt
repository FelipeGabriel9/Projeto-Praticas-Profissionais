package com.example.mymoneyandroid.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mymoneyandroid.viewmodel.CategoriaViewModel

// Cores usadas na tela
private val CorFundoVerde = Color(0xFF2E7D32)
private val CorBotaoCinza = Color(0xFF3A3A3C)
private val CorTextoBranco = Color(0xFFFFFFFF)
private val verdeGradiente = Color(0xFF1A2E1A)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriaScreen(
    controleNavegacao: NavController,
    viewModel: CategoriaViewModel = viewModel() // Conecta o ViewModel à tela
) {
    var mostrarDialogo by remember { mutableStateOf(false) }
    var novoNome by remember { mutableStateOf("") }

    // "Ouvindo" a lista que vem da API
    val listaCategorias by viewModel.categorias.collectAsState()

    // Chama a função do menu para ser criado a barra superior
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
                // Criamos itens baseados no tamanho da lista + 1 (para o botão Criar)
                items(listaCategorias.size + 1) { index ->
                    if (index == listaCategorias.size) {
                        // Último botão sempre será o "Criar"
                        BotaoCategoria(nome = "Criar") {
                            mostrarDialogo = true
                        }
                    } else {
                        // Pega a categoria da API
                        val categoria = listaCategorias[index]
                        BotaoCategoria(nome = categoria.NomeCategoria) {
                            controleNavegacao.navigate("detalhescategoria/${categoria.NomeCategoria}")
                        }
                    }
                }
            }

            // Gráfico com os nomes reais da API
            CardGraficoCategorias(
                nome1 = listaCategorias.getOrNull(0)?.NomeCategoria ?: "Categoria 1",
                nome2 = listaCategorias.getOrNull(1)?.NomeCategoria ?: "Categoria 2"
            )
        }
    }

    // Criando nova categoria via API
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
                        viewModel.criarCategoria(novoNome) // Envia para o C#
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
        colors = CardDefaults.cardColors(containerColor = CorTextoBranco),
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
        Canvas(
            modifier = Modifier.size(8.dp))
        {
            drawCircle(color = cor)
        }
        Text(text = " $texto", fontSize = 11.sp, color = Color.DarkGray)
    }
}

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

// Estrutura de dados para o gráfico de pizza funcionar
data class DividirGrafico(val angulo: Float, val cor: Color)