package com.example.mymoneyandroid.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


// Cores usadas na tela
private val fundoTela = Color(0xFF0F0F0F)
private val fundoHeader = Color(0xFF1C1C1E)
private val fundoPeriodos = Color(0xFF2C2C2E)
private val corPeriodoSelecionado = Color(0xFF2E7D32)
private val corTexto = Color(0xFFFFFFFF)
private val valorTotal    = Color(0xFF8E8E93)
private val valorEntrada  = Color(0xFFE8693A)
private val valorSaida    = Color(0xFF4A90D9)
private val valorOutros    = Color(0xFF8E8E93)

@Composable
fun PrincipalScreen(
    valorTotal: String = "R$ 0000,00",
    botaoMenu: () -> Unit = {},
    navController: NavController
) {
    AbaMenu(tituloDaPagina = "", navController = navController) { paddingValues ->
        var selecionarPeriodo by remember { mutableStateOf("Mês") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(fundoTela)
                .verticalScroll(rememberScrollState())
        ) {

            // ── Top Bar ─────────────────────────────────────────────────────────
            BarraSuperior(onMenuClick = botaoMenu)

            Spacer(modifier = Modifier.height(24.dp))
            // ── Banner com logo ─────────────────────────────────────────────────
            ConfiguracaoHeader()

            // ── Total ───────────────────────────────────────────────────────────
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Saldo em conta",
                color = corTexto,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "R$ $valorTotal",
                color = com.example.mymoneyandroid.view.valorTotal,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            // ── Filtro de período ────────────────────────────────────────────────
            FiltrarPeriodo(
                periodoSelecionado = selecionarPeriodo,
                selecionar = { selecionarPeriodo = it }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // ── Gráfico ──────────────────────────────────────────────────────────
            CardComGraficos()
        }
    }
}


@Composable
private fun ConfiguracaoHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(fundoHeader)
            .padding(vertical = 30.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "MyMoney",
                color = corTexto,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 0.5.sp
            )
            Text(
                text = "CONTROLE FINANCEIRO",
                color = corTexto,
                fontSize = 10.sp,
                letterSpacing = 2.sp
            )
        }
    }
}



@Composable
private fun FiltrarPeriodo(
    periodoSelecionado: String,
    selecionar: (String) -> Unit
) {
    val periodos = listOf("Dia", "Semana", "Mês", "Ano")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = fundoPeriodos),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            periodos.forEach { periodo ->
                val estaSelecionado = periodo == periodoSelecionado
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = 
                                if (estaSelecionado)
                                    corPeriodoSelecionado
                                else 
                                    Color.Transparent,
                            
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable { selecionar(periodo) }
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = periodo,
                        color = corTexto,
                        fontSize = 14.sp,
                        fontWeight = 
                            if (estaSelecionado) 
                                FontWeight.Bold 
                            else 
                                FontWeight.Normal
                    )
                }
            }
        }
    }
}


@Composable
private fun CardComGraficos() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = corTexto),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GraficoPizza(
                modifier = Modifier
                    .size(180.dp)
                    .padding(8.dp),
                camposDoGrafico = listOf(
                    DividirGrafico(0.45f, valorEntrada),   // Entradas
                    DividirGrafico(0.35f, valorSaida),     // Saídas
                    DividirGrafico(0.20f, valorOutros)      // Outros
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Legenda(color = valorEntrada, label = "Entradas")
                Legenda(color = valorSaida,   label = "Saídas")
                Legenda(color = valorOutros,   label = "Outros")
            }
        }
    }
}

data class DividirGrafico(val angulo: Float, val cor: Color)

@Composable
private fun GraficoPizza(
    modifier: Modifier = Modifier,
    camposDoGrafico: List<DividirGrafico>
) {
    Canvas(modifier = modifier) {
        var anguloInicial = -90f
        camposDoGrafico.forEach { campo ->
            val anguloOcupado = campo.angulo * 360f
            drawArc(
                color = campo.cor,
                startAngle = anguloInicial,
                sweepAngle = anguloOcupado,
                useCenter = true,
                topLeft = Offset(0f, 0f),
                size = Size(this.size.width, this.size.height)
            )
            anguloInicial += anguloOcupado
        }
    }
}

@Composable
private fun Legenda(color: Color, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Canvas(modifier = Modifier.size(10.dp)) {
            drawCircle(color = color)
        }
        Text(text = label, fontSize = 11.sp, color = Color(0xFF555555))
    }
}


