package com.example.mymoneyandroid.view

import android.net.Uri
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mymoneyandroid.model.Meta
import com.example.mymoneyandroid.viewmodel.MetaViewModel

private val CorFundoVerde = Color(0xFF2E7D32)
private val CorCardEscuro = Color(0xFF1C1C1E)
private val CorBotaoCinza = Color(0xFF3A3A3C)
private val CorTextoBranco = Color(0xFFFFFFFF)
private val CorVerdeBotao = Color(0xFF34C759)
private val verdeGradiente = Color(0xFF1A2E1A)

@Composable
fun MetasScreen(
    controleNavegacao: NavController,
    idUsuario: Int,
    viewModel: MetaViewModel = viewModel()
) {
    var mostrarDialogo by remember { mutableStateOf(false) }
    // Voltamos com a variável para o nome da meta aqui pro topo!
    var novoNomeMeta by remember { mutableStateOf("") }

    LaunchedEffect(idUsuario) {
        viewModel.buscarMetas(idUsuario)
    }

    val listaMetasApi by viewModel.metas.collectAsState()

    // 1. Criamos nossas opções de metas fixas (idMeta = 0 indica que ainda não estão no banco)
    val metasFixas = remember(idUsuario) {
        listOf(
            Meta(idMeta = 0, idUsuario = idUsuario, nomeMeta = "Reserva", valorObjetivo = 0.0, valorAtual = 0.0),
            Meta(idMeta = 0, idUsuario = idUsuario, nomeMeta = "Carro", valorObjetivo = 0.0, valorAtual = 0.0),
            Meta(idMeta = 0, idUsuario = idUsuario, nomeMeta = "Viagem", valorObjetivo = 0.0, valorAtual = 0.0)
        )
    }

    // 2. Filtramos e juntamos as listas.
    // Se a meta já veio do banco, a opção fixa com o mesmo nome some!
    val listaCompleta = remember(listaMetasApi, metasFixas) {
        val nomesJaSalvos = listaMetasApi.map { it.nomeMeta }
        val fixasDisponiveis = metasFixas.filter { it.nomeMeta !in nomesJaSalvos }

        // Colocamos as metas do banco primeiro, e as sugestões fixas depois
        listaMetasApi + fixasDisponiveis
    }

    MenuScreen(tituloDaPagina = "Minhas Metas", controleNagegacao = controleNavegacao, idUsuario = idUsuario) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = linearGradient(colors = listOf(CorFundoVerde, verdeGradiente)))
                .padding(padding)
        ) {

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(
                    items = listaCompleta, // ...para usar a listaCompleta!
                    key = { meta -> "${meta.idMeta}_${meta.nomeMeta}" } // Chave atualizada para evitar bugs de nomes iguais
                ) { meta ->
                    val nomeValido = meta.nomeMeta ?: "Sem nome"

                    BotaoMeta(nome = nomeValido) {

                        // Se já tem ID, navega direto para a rota de meta dinâmica
                        if (meta.idMeta != null && meta.idMeta != 0) {
                            val nomeTratado = Uri.encode(nomeValido)
                            controleNavegacao.navigate("detalhemeta/${meta.idMeta}/$nomeTratado/$idUsuario")
                        } else {
                            // Se NÃO tem ID, é uma meta fixa. Criamos ela e depois navegamos
                            viewModel.criarMetaFixa(
                                nomeMeta = nomeValido,
                                idUsuario = idUsuario,
                                aoCriar = { idGerado ->
                                    val nomeTratado = Uri.encode(nomeValido)
                                    controleNavegacao.navigate("detalhemeta/$idGerado/$nomeTratado/$idUsuario")
                                }
                            )
                        }
                    }
                }

                // Botão de Criar Meta
                item {
                    BotaoMeta(nome = "Criar ") {
                        mostrarDialogo = true
                    }
                }
            }

            CardGraficoMetas(metas = listaMetasApi)

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Aqui o AlertDialog fica direto na tela principal, sem função externa!
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
                        if (novoNomeMeta.isNotBlank()) {
                            viewModel.criarMeta(novoNomeMeta.trim(), idUsuario)
                            novoNomeMeta = "" // Limpa o campo para a próxima vez
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
private fun CardGraficoMetas(metas: List<Meta>) {
    val objetivoTotal = metas.sumOf { it.valorObjetivo }
    val metasOrdenadas = metas.sortedByDescending { it.valorObjetivo }

    val maiorMeta1 = metasOrdenadas.getOrNull(0)
    val maiorMeta2 = metasOrdenadas.getOrNull(1)

    val fatia1 = if (objetivoTotal > 0 && maiorMeta1 != null) (maiorMeta1.valorObjetivo / objetivoTotal).toFloat() else 0.33f
    val fatia2 = if (objetivoTotal > 0 && maiorMeta2 != null) (maiorMeta2.valorObjetivo / objetivoTotal).toFloat() else 0.33f
    val fatiaOutros = if (objetivoTotal > 0) 1.0f - fatia1 - fatia2 else 0.34f

    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CorTextoBranco),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Distribuição das metas", fontWeight = FontWeight.Bold, color = Color.Black)

            Spacer(modifier = Modifier.height(16.dp))

            GraficoPizza(
                modifier = Modifier.size(140.dp),
                camposDoGrafico = listOf(
                    fatia1 to Color(0xFFE8693A),
                    fatia2 to Color(0xFF4A90D9),
                    fatiaOutros to Color(0xFF8E8E93)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                LegendaItem(Color(0xFFE8693A), maiorMeta1?.nomeMeta ?: "Nenhuma")
                LegendaItem(Color(0xFF4A90D9), maiorMeta2?.nomeMeta ?: "Nenhuma")
                LegendaItem(Color(0xFF8E8E93), "Outras")
            }
        }
    }
}

@Composable
private fun BotaoMeta(nome: String, onClick: () -> Unit) {
    Surface(onClick = onClick, color = CorBotaoCinza, shape = RoundedCornerShape(12.dp), modifier = Modifier.height(60.dp).fillMaxWidth()) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = nome, color = CorTextoBranco, fontSize = 13.sp, fontWeight = FontWeight.Bold)
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

@Composable
private fun GraficoPizza(modifier: Modifier = Modifier, camposDoGrafico: List<Pair<Float, Color>>) {
    Canvas(modifier = modifier) {
        var anguloInicial = -90f
        camposDoGrafico.forEach { campo ->
            val anguloOcupado = campo.first * 360f
            drawArc(color = campo.second, startAngle = anguloInicial, sweepAngle = anguloOcupado, useCenter = true, size = Size(size.width, size.height))
            anguloInicial += anguloOcupado
        }
    }
}