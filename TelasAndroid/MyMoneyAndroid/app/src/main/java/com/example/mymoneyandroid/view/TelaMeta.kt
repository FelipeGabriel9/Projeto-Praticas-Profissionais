package com.example.mymoneyandroid.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
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

// Cores usadas na tela
private val CorFundoVerde = Color(0xFF2E7D32)
private val CorCardEscuro = Color(0xFF1C1C1E)
private val CorBotaoCinza = Color(0xFF3A3A3C)
private val CorTextoBranco = Color(0xFFFFFFFF)
private val CorVerdeBotao = Color(0xFF34C759)
private val verdeGradiente = Color(0xFF1A2E1A)

// Lista de metas fixas
val listaMetas = mutableStateListOf(
    "Viagens", "Casamento", "Compras", "Criar"
)

// Criando a função principal da tela
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MetasScreen(controleNavegacao: NavController) {

    var mostrarDialogo by remember { mutableStateOf(false) }
    var novoNomeMeta by remember { mutableStateOf("") }

    // Chama a função do menu para ser criado a barra superior
    MenuScreen(tituloDaPagina = "Minhas Metas", controleNagegacao = controleNavegacao) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = linearGradient(
                    colors = listOf(CorFundoVerde, verdeGradiente)
                    )
                )
                .padding(padding)
        ) {
            // Grid de metas
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.height(200.dp)
            ) {
                items(listaMetas) { meta ->
                    BotaoMeta(nome = meta) {
                        if (meta == "Criar") {
                            mostrarDialogo = true
                        } else {
                            // Navega para a rota detalhemeta/nomeMeta
                            controleNavegacao.navigate("detalhemeta/$meta")
                        }
                    }
                }
            }

            CardGraficoMetas(
                nome1 = listaMetas.getOrNull(0) ?: " ",
                nome2 = listaMetas.getOrNull(1) ?: " "
            )
        }

        // Criando nova meta
        if (mostrarDialogo) {
            AlertDialog(
                onDismissRequest = { mostrarDialogo = false },
                containerColor = CorCardEscuro,
                title = { Text("Nova Meta", color = CorTextoBranco) },
                text = {
                    OutlinedTextField(
                        value = novoNomeMeta,
                        onValueChange = { novoNomeMeta = it },
                        label = { Text("Nome da Meta") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = CorTextoBranco,
                            unfocusedTextColor = CorTextoBranco,
                            focusedBorderColor = CorVerdeBotao,
                            focusedLabelColor = CorVerdeBotao
                        )
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        if (novoNomeMeta.isNotEmpty()) {
                            listaMetas.add(listaMetas.size - 1, novoNomeMeta)
                            novoNomeMeta = ""
                            mostrarDialogo = false
                        }
                    }) {
                        Text("CRIAR", color = CorVerdeBotao, fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { mostrarDialogo = false }) {
                        Text("CANCELAR", color = Color.Gray)
                    }
                }
            )
        }
    }
}

@Composable
private fun CardGraficoMetas(nome1: String, nome2: String) {
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
            Text("Distribuição das metas", fontWeight = FontWeight.Bold, color = Color.Black)

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
private fun BotaoMeta(nome: String, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        color = CorBotaoCinza, // Usando a variável
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.height(60.dp).fillMaxWidth()
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = nome,
                color = CorTextoBranco, // Usando a variável
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

// Criando a função que mostra a legenda
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


// Função que cria a lógica do Gráfico de Pizza
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