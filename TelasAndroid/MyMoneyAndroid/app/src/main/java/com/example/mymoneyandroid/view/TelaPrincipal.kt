package com.example.mymoneyandroid.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


private val BgDark       = Color(0xFF0F0F0F)
private val BgMid        = Color(0xFF1C1C1E)
private val BgCard       = Color(0xFF2C2C2E)
private val GreenPrimary = Color(0xFF2E7D32)
private val AccentGreen  = Color(0xFF34C759)
private val TextWhite    = Color(0xFFFFFFFF)
private val TextMuted    = Color(0xFF8E8E93)
private val ChartOrange  = Color(0xFFE8693A)
private val ChartBlue    = Color(0xFF4A90D9)
private val ChartGray    = Color(0xFF8E8E93)

@Composable
fun PrincipalScreen(
    totalValue: String = "xxxx,xx",
    onMenuClick: () -> Unit = {}
) {
    var selectedPeriod by remember { mutableStateOf("Mês") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgDark)
    ) {

        // ── Top Bar ─────────────────────────────────────────────────────────
        PrincipalTopBar(onMenuClick = onMenuClick)

        // ── Banner com logo ─────────────────────────────────────────────────
        LogoBanner()

        // ── Total ───────────────────────────────────────────────────────────
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Total",
            color = TextWhite,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "R$ $totalValue",
            color = TextMuted,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        // ── Filtro de período ────────────────────────────────────────────────
        PeriodFilter(
            selected = selectedPeriod,
            onSelect = { selectedPeriod = it }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // ── Gráfico ──────────────────────────────────────────────────────────
        ChartCard()
    }
}


@Composable
private fun PrincipalTopBar(onMenuClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        LogoIcon()

        Text(
            text = "≡",
            color = TextWhite,
            fontSize = 28.sp,
            modifier = Modifier.clickable { onMenuClick() }
        )
    }
}

@Composable
private fun LogoIcon(size: Float = 28f) {
    Canvas(
        modifier = Modifier.size((size * 1.6f).dp)
    ) {
        val r = size * 0.38f
        // Círculo esquerdo
        drawCircle(
            color = AccentGreen,
            radius = r,
            center = Offset(r * 1.1f, this.size.height / 2)
        )
        drawCircle(
            color = GreenPrimary,
            radius = r,
            center = Offset(r * 1.8f, this.size.height / 2)
        )
    }
}


@Composable
private fun LogoBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(BgMid)
            .padding(vertical = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Canvas(modifier = Modifier.size(72.dp)) {
                val cx = this.size.width / 2
                val cy = this.size.height / 2
                val r = this.size.width * 0.28f
                drawCircle(color = AccentGreen, radius = r,
                    center = Offset(cx - r * 0.55f, cy))
                drawCircle(color = GreenPrimary, radius = r,
                    center = Offset(cx + r * 0.55f, cy))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "myMoney",
                color = TextWhite,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 0.5.sp
            )
            Text(
                text = "CONTROLE FINANCEIRO",
                color = TextMuted,
                fontSize = 10.sp,
                letterSpacing = 2.sp
            )
        }
    }
}


@Composable
private fun PeriodFilter(
    selected: String,
    onSelect: (String) -> Unit
) {
    val periods = listOf("Dia", "Semana", "Mês", "Ano")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = BgCard),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            periods.forEach { period ->
                val isSelected = period == selected
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            if (isSelected) GreenPrimary else Color.Transparent,
                            RoundedCornerShape(8.dp)
                        )
                        .clickable { onSelect(period) }
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = period,
                        color = if (isSelected) TextWhite else TextMuted,
                        fontSize = 14.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                    )
                }
            }
        }
    }
}


@Composable
private fun ChartCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
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
            PieChart(
                modifier = Modifier
                    .size(180.dp)
                    .padding(8.dp),
                slices = listOf(
                    PieSlice(0.45f, ChartOrange),   // Entradas
                    PieSlice(0.35f, ChartBlue),     // Saídas
                    PieSlice(0.20f, ChartGray)      // Outros
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Gráfico geral, com entradas e saídas",
                color = Color(0xFF333333),
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                LegendItem(color = ChartOrange, label = "Entradas")
                LegendItem(color = ChartBlue,   label = "Saídas")
                LegendItem(color = ChartGray,   label = "Outros")
            }
        }
    }
}

data class PieSlice(val fraction: Float, val color: Color)

@Composable
private fun PieChart(
    modifier: Modifier = Modifier,
    slices: List<PieSlice>
) {
    Canvas(modifier = modifier) {
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
}

@Composable
private fun LegendItem(color: Color, label: String) {
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


@Preview(showBackground = true, widthDp = 360, heightDp = 720)
@Composable
fun PrincipalScreenPreview() {
    PrincipalScreen()
}