package com.example.mymoneyandroid.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Canvas
import androidx.navigation.NavController

private val InicialGreenPrimary = Color(0xFF22C55E)
private val InicialGreenLight   = Color(0xFF4ADE80)
private val InicialGreenDark    = Color(0xFF16A34A)
private val InicialDarkBg       = Color(0xFF1A1A1A)
private val InicialDarkBgGreen  = Color(0xFF1A2E1A)

data class PieSliceInicial(val fraction: Float, val color: Color)

@Composable
fun TelaInicial(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF1F5F1))
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(InicialDarkBg)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PiggyBankIcon()

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                repeat(3) {
                    Box(
                        modifier = Modifier
                            .width(18.dp)
                            .height(2.dp)
                            .background(Color.White, RoundedCornerShape(1.dp))
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = androidx.compose.ui.graphics.Brush.linearGradient(
                        colors = listOf(InicialDarkBg, InicialDarkBgGreen)
                    )
                )
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "MyMoney",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = InicialGreenPrimary,
                letterSpacing = (-0.5).sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Tela inicial",
                fontSize = 13.sp,
                color = Color.White.copy(alpha = 0.55f)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedButton(
                    onClick = { navController.navigate("cadastro") },
                    border = androidx.compose.foundation.BorderStroke(1.dp, InicialGreenPrimary),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = InicialGreenPrimary)
                ) {
                    Text("Criar nova conta", fontSize = 13.sp)
                }
                Button(
                    onClick = { navController.navigate("login") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = InicialGreenPrimary,
                        contentColor = InicialDarkBg
                    ),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text("Entrar", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            ChartCardInicial(
                label = "Gastos separados por setor",
                slices = listOf(
                    PieSliceInicial(0.35f, InicialGreenPrimary),
                    PieSliceInicial(0.20f, Color(0xFFE07028)),
                    PieSliceInicial(0.25f, Color(0xFF9BABB8)),
                    PieSliceInicial(0.20f, Color(0xFFBBF7D0))
                ),
                legends = listOf(
                    Pair(InicialGreenPrimary, "Alimentação"),
                    Pair(Color(0xFFE07028), "Transporte"),
                    Pair(Color(0xFF9BABB8), "Lazer"),
                    Pair(Color(0xFFBBF7D0), "Outros")
                )
            )

            ChartCardInicial(
                label = "Total em conta",
                slices = listOf(
                    PieSliceInicial(0.45f, InicialGreenPrimary),
                    PieSliceInicial(0.35f, Color(0xFFE07028)),
                    PieSliceInicial(0.20f, Color(0xFF9BABB8))
                ),
                legends = listOf(
                    Pair(InicialGreenPrimary, "Poupança"),
                    Pair(Color(0xFFE07028), "Corrente"),
                    Pair(Color(0xFF9BABB8), "Investimento")
                )
            )
        }
    }
}

@Composable
fun ChartCardInicial(
    label: String,
    slices: List<PieSliceInicial>,
    legends: List<Pair<Color, String>>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                .background(InicialGreenPrimary)
                .height(3.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Canvas(modifier = Modifier.size(160.dp)) {
            drawPieInicial(slices)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            legends.forEach { (color, text) ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(color)
                    )
                    Text(text, fontSize = 10.sp, color = Color(0xFF6B7280))
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            color = InicialGreenDark,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}

fun DrawScope.drawPieInicial(slices: List<PieSliceInicial>) {
    var startAngle = -90f
    slices.forEach { slice ->
        val sweep = slice.fraction * 360f
        drawArc(
            color = slice.color,
            startAngle = startAngle,
            sweepAngle = sweep,
            useCenter = true,
            topLeft = Offset(0f, 0f),
            size = Size(this.size.width, this.size.height)
        )
        startAngle += sweep
    }
}

@Composable
fun PiggyBankIcon() {
    Canvas(modifier = Modifier.size(34.dp, 30.dp)) {
        drawOval(
            color = Color(0xFF22C55E),
            topLeft = Offset(5.dp.toPx(), 7.dp.toPx()),
            size = Size(22.dp.toPx(), 18.dp.toPx())
        )
        drawOval(
            color = Color(0xFF4ADE80),
            topLeft = Offset(23.dp.toPx(), 11.dp.toPx()),
            size = Size(7.dp.toPx(), 5.6f.dp.toPx())
        )
    }
}