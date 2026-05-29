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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mymoneyandroid.model.Categoria
import com.example.mymoneyandroid.viewmodel.CategoriaViewModel

private val CorFundoVerde = Color(0xFF2E7D32)
private val CorCardEscuro = Color(0xFF1C1C1E)
private val CorBotaoCinza = Color(0xFF3A3A3C)
private val CorTextoBranco = Color(0xFFFFFFFF)
private val CorVerdeBotao = Color(0xFF34C759)
private val verdeGradiente = Color(0xFF1A2E1A)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriaScreen(
    controleNavegacao: NavController,
    idUsuario: Int,
    viewModel: CategoriaViewModel = viewModel() // Adicionado o ViewModel aqui
) {
    var mostrarDialogo by remember { mutableStateOf(false) }
    var novoNomeCategoria by remember { mutableStateOf("") }

    // Dispara a busca das categorias assim que a tela abre passando o idUsuario
    LaunchedEffect(idUsuario) {
        viewModel.buscarCategorias(idUsuario)
    }

    val listaCategoriasApi by viewModel.categorias.collectAsState()

    MenuScreen(
        tituloDaPagina = "Categorias",
        controleNagegacao = controleNavegacao,
        idUsuario = idUsuario
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = linearGradient(
                        colors = listOf(
                            CorFundoVerde,
                            verdeGradiente
                        )
                    )
                )
                .padding(padding)
        ) {

            // Grid de categorias
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.weight(1f) // Permite que a Grid se ajuste deixando espaço pro card de baixo
            ) {

                // Renderiza todas as categorias do banco/fixas
                items(
                    items = listaCategoriasApi,

                    key = { categoria ->
                        categoria.idCategoria ?: categoria.nomeCategoria ?: ""
                    }

                )  { categoria ->

                    BotaoCategoria(
                        nome = categoria.nomeCategoria ?: "Sem nome"
                    ) {

                        // Navega para a tela de detalhes enviando o nome da categoria selecionada
                        if (categoria.idCategoria != null) {

                            controleNavegacao.navigate(
                                "detalhescategoria/${categoria.idCategoria}/$idUsuario"
                            )

                        } else {

                            viewModel.criarCategoriaFixa(
                                nomeCategoria = categoria.nomeCategoria ?: "",
                                idUsuario = idUsuario,
                                aoCriar = { idGerado ->

                                    controleNavegacao.navigate("detalhescategoria/$idGerado/$idUsuario")
                                }
                            )
                        }
                    }
                }

                item {

                    BotaoCategoria(nome = "Criar ") {
                        mostrarDialogo = true
                    }
                }
            }

            // Enviamos a lista completa para o card do gráfico calcular os valores
            CardGraficoCategorias(
                categorias = listaCategoriasApi
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Cria nova Categoria
        if (mostrarDialogo) {

            AlertDialog(
                onDismissRequest = {
                    mostrarDialogo = false
                },

                containerColor = CorCardEscuro,

                title = {
                    Text(
                        "Nova Categoria",
                        color = CorTextoBranco
                    )
                },

                text = {

                    OutlinedTextField(
                        value = novoNomeCategoria,

                        onValueChange = {
                            novoNomeCategoria = it
                        },

                        label = {
                            Text("Nome da Categoria")
                        },

                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = CorTextoBranco,
                            unfocusedTextColor = CorTextoBranco,
                            focusedBorderColor = CorVerdeBotao,
                            focusedLabelColor = CorVerdeBotao
                        )
                    )
                },

                confirmButton = {

                    TextButton(
                        onClick = {

                            if (
                                novoNomeCategoria
                                    .trim()
                                    .isNotEmpty()
                            ) {

                                viewModel.criarCategoria(
                                    novoNomeCategoria.trim(),
                                    idUsuario
                                )

                                novoNomeCategoria = ""

                                mostrarDialogo = false
                            }
                        }
                    ) {

                        Text(
                            "CRIAR",
                            color = CorVerdeBotao,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },

                dismissButton = {

                    TextButton(
                        onClick = {
                            mostrarDialogo = false
                        }
                    ) {

                        Text(
                            "CANCELAR",
                            color = Color.Gray
                        )
                    }
                }
            )
        }
    }
}

@Composable
private fun CardGraficoCategorias(
    categorias: List<Categoria>
) {

    val categoriasComGasto = categorias.filter {
        it.valorDespesa > 0
    }

    // Soma o total gasto de todas as categorias juntas
    val gastoTotal = categoriasComGasto.sumOf {
        it.valorDespesa
    }

    // Ordena as categorias por maior gasto para destacar no gráfico
    val categoriasOrdenadas =
        categoriasComGasto.sortedByDescending {
            it.valorDespesa
        }

    val maiorGasto1 =
        categoriasOrdenadas.getOrNull(0)

    val maiorGasto2 =
        categoriasOrdenadas.getOrNull(1)

    // Calcula os percentuais reais (se não tiver nenhum gasto, divide igualmente)
    val percentual1 =
        if (
            gastoTotal > 0 &&
            maiorGasto1 != null
        ) {

            (
                    maiorGasto1.valorDespesa /
                            gastoTotal
                    ).toFloat()

        } else {

            0.33f
        }

    val percentual2 =
        if (
            gastoTotal > 0 &&
            maiorGasto2 != null
        ) {

            (
                    maiorGasto2.valorDespesa /
                            gastoTotal
                    ).toFloat()

        } else {

            0.33f
        }

    val percentualOutros =
        if (gastoTotal > 0) {

            1.0f - percentual1 - percentual2

        } else {

            0.34f
        }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),

        shape = RoundedCornerShape(16.dp),

        colors = CardDefaults.cardColors(
            containerColor = CorTextoBranco
        ),

        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                "Distribuição de gastos reais",
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Gráfico atualizado com os valores calculados dinamicamente
            GraficoPizza(
                modifier = Modifier.size(140.dp),

                camposDoGrafico = listOf(

                    percentual1 to Color(0xFFE8693A), // Maior gasto (Laranja)

                    percentual2 to Color(0xFF4A90D9), // Segundo maior gasto (Azul)

                    percentualOutros to Color(0xFF8E8E93) // O resto (Cinza)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Legendas mostrando os nomes das categorias que mais gastaram
            Row(
                horizontalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {

                LegendaItem(
                    Color(0xFFE8693A),
                    maiorGasto1?.nomeCategoria ?: "Nenhum"
                )

                LegendaItem(
                    Color(0xFF4A90D9),
                    maiorGasto2?.nomeCategoria ?: "Nenhum"
                )

                LegendaItem(
                    Color(0xFF8E8E93),
                    "Outras"
                )
            }
        }
    }
}

@Composable
private fun BotaoCategoria(
    nome: String?,
    onClick: () -> Unit
) {

    Surface(
        onClick = onClick,
        color = CorBotaoCinza,
        shape = RoundedCornerShape(12.dp),

        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
    ) {

        Box(
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = nome ?: "Sem nome",
                color = CorTextoBranco,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun LegendaItem(
    cor: Color,
    texto: String
) {

    Row(
        verticalAlignment =
            Alignment.CenterVertically
    ) {

        Canvas(
            modifier = Modifier.size(8.dp)
        ) {

            drawCircle(color = cor)
        }

        Text(
            text = " $texto",
            fontSize = 11.sp,
            color = Color.DarkGray
        )
    }
}

@Composable
private fun GraficoPizza(
    modifier: Modifier = Modifier,
    camposDoGrafico: List<Pair<Float, Color>>
) {

    Canvas(modifier = modifier) {

        var anguloInicial = -90f

        camposDoGrafico.forEach { campo ->

            val anguloOcupado =
                campo.first * 360f

            drawArc(
                color = campo.second,
                startAngle = anguloInicial,
                sweepAngle = anguloOcupado,
                useCenter = true,
                size = Size(
                    size.width,
                    size.height
                )
            )

            anguloInicial += anguloOcupado
        }
    }
}