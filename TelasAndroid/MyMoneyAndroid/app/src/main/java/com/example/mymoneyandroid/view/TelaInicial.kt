package com.example.mymoneyandroid.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview

private val verdePrincipal = Color(0xFF22C55E)
private val corTexto = Color(0xFFFFFFFF)
private val corDeFundo = Color(0xFFF1F5F1)
private val escuroGradiente = Color(0xFF1A1A1A)
private val verdeGradiente = Color(0xFF1A2E1A)

data class ParteGrafico(val fracao: Float, val cor: Color)

// Criando a tela inicial
@Composable
fun InicialScreen(controleNavegacao: NavController) {

    // Preenche o espaço da tela
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(corDeFundo)
            .verticalScroll(rememberScrollState())
    ) {

        // Cria o header, onde teremos um fundo gradiente e os botões que levam para a tela
        // de cadastro e de login
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = linearGradient(
                        colors = listOf(escuroGradiente, verdeGradiente)
                    )
                )
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título da página
            Text(
                text = "MyMoney",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = verdePrincipal
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Subtítulo
            Text(
                text = "Seu sistema de controle financeiro",
                fontSize = 13.sp,
                color = corTexto.copy(alpha = 0.55f)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Cria a linha onde os botões ficarão inseridos
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {

                // Botão de criar nova conta
                OutlinedButton(
                    onClick = { controleNavegacao.navigate("telaCadastro") },
                    border = androidx.compose.foundation.BorderStroke(1.dp, verdePrincipal),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = verdePrincipal)
                ) {
                    Text("Criar nova conta", fontSize = 13.sp)
                }

                // Botão de entrar em uma conta já existente
                Button(
                    onClick = { controleNavegacao.navigate("telaLogin") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = verdePrincipal,
                        contentColor = escuroGradiente
                    ),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text("Entrar", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }

        // Cria a parte dos gráficos
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            CardComGraficos()

            CardGraficoInicial(
                titulo = "Total em conta",
                fatias = listOf(
                    ParteGrafico(0.45f, verdePrincipal),
                    ParteGrafico(0.35f, Color(0xFFE07028)),
                    ParteGrafico(0.20f, Color(0xFF9BABB8))
                ),
                legendas = listOf(
                    Pair(verdePrincipal, "Poupança"),
                    Pair(Color(0xFFE07028), "Corrente"),
                    Pair(Color(0xFF9BABB8), "Investimento")
                )
            )
        }
    }
}

@Composable
private fun CardGraficoInicial(titulo: String, fatias: List<ParteGrafico>, legendas: List<Pair<Color, String>>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(titulo, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(12.dp))
            GraficoPizza(
                modifier = Modifier.size(150.dp),
                camposDoGrafico = fatias.map { DividirGrafico(it.fracao, it.cor) }
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                legendas.forEach { Legenda(it.first, it.second) }
            }
        }
    }
}

@Composable
private fun CardComGraficos() {
    // Primeiro, cria-se um card, e dentro dele inserimos os gráficos
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Chama a função GraficoPizza, e passa alguns valores hipotéticos
            GraficoPizza(
                modifier = Modifier
                    .size(180.dp)
                    .padding(8.dp),
                camposDoGrafico = listOf(
                    // Chama o data class, que recebe um angulo e uma cor
                    DividirGrafico(0.45f, verdePrincipal),   // Entradas
                    DividirGrafico(0.35f, Color(0xFFE07028)),     // Saídas
                    DividirGrafico(0.20f, Color(0xFF9BABB8))     // Outros
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Cria uma linha onde será inserido a legenda
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Legenda(cor = verdePrincipal, textoLegenda = "Entradas")
                Legenda(cor = Color(0xFFE07028),   textoLegenda = "Saídas")
                Legenda(cor = Color(0xFF9BABB8),   textoLegenda = "Outros")
            }
        }
    }
}

data class DividirGrafico(val angulo: Float, val cor: Color)

// Cria o gráfico com os valores hipotéticos passados pela função anterior
@Composable
private fun GraficoPizza(
    modifier: Modifier = Modifier,
    camposDoGrafico: List<DividirGrafico>
) {
    // Chama a classe Canvas, que com algumas informações, vai criar os gráficos
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

// Cria a função de Legenda
@Composable
private fun Legenda(cor: Color, textoLegenda: String) {
    // Cria uma linha, onde se armazenará a legenda
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // Usa-se Canvas para criar um pequeno círculo, mostrando a cor de determinado item da
        // legenda
        Canvas(modifier = Modifier.size(10.dp)) {
            drawCircle(color = cor)
        }
        Text(text = textoLegenda, fontSize = 11.sp, color = Color(0xFF555555))
    }
}

@Preview(showBackground = true)
@Composable
fun InicialScreenPreview() {
    InicialScreen(controleNavegacao = rememberNavController())
}