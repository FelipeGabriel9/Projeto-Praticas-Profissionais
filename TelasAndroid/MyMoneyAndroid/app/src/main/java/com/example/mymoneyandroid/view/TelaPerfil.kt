package com.example.mymoneyandroid.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

private val fundoCard   = Color(0xFF1C1C1E)
private val fundoCampos = Color(0xFF2C2C2E)
private val bordaCampos = Color(0xFF3A3A3C)
private val corTexto    = Color(0xFFFFFFFF)
private val corTextoSec = Color(0xFF8E8E93)
private val fundoBotao  = Color(0xFF34C759)
private val corPerigo   = Color(0xFFFF453A)

data class UsuarioPerfil(
    val nome: String        = "",
    val cpf: String         = "",
    val email: String       = "",
    val membroDesde: String = "",
    val plano: String       = "Grátis"
)

@Composable
fun PerfilScreen(
    controleNavegacao: NavController,
    perfil: UsuarioPerfil? = null,
    isLoading: Boolean     = false,
    erro: String?          = null
) {
    val dadosMock = UsuarioPerfil(
        nome        = "João Silva",
        cpf         = "***.456.789-**",
        email       = "joao@email.com",
        membroDesde = "Maio 2025",
        plano       = "Premium"
    )
    val dados = perfil ?: dadosMock

    MenuScreen(tituloDaPagina = "", controleNagegacao = controleNavegacao) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFF0F0F0F))
                .verticalScroll(rememberScrollState())
                .padding(bottom = 32.dp)
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(fundoBotao),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = dados.nome
                            .split(" ")
                            .take(2)
                            .mapNotNull { it.firstOrNull()?.uppercaseChar() }
                            .joinToString(""),
                        color = corTexto,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (isLoading) {
                    PerfilLoadingPlaceholder(width = 140.dp, height = 20.dp)
                    Spacer(modifier = Modifier.height(6.dp))
                    PerfilLoadingPlaceholder(width = 100.dp, height = 14.dp)
                } else {
                    Text(text = dados.nome, color = corTexto, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = if (dados.plano == "Premium") fundoBotao.copy(alpha = 0.25f) else fundoCampos
                    ) {
                        Text(
                            text = dados.plano,
                            color = if (dados.plano == "Premium") fundoBotao else corTextoSec,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            PerfilCard(titulo = "Informações pessoais") {
                PerfilCampo("Nome completo", dados.nome, isLoading)
                Divider(color = bordaCampos, thickness = 0.5.dp)
                PerfilCampo("E-mail", dados.email, isLoading)
                Divider(color = bordaCampos, thickness = 0.5.dp)
                PerfilCampo("CPF", dados.cpf, isLoading)
            }

            Spacer(modifier = Modifier.height(16.dp))

            PerfilCard(titulo = "Conta") {
                PerfilCampo("Membro desde", dados.membroDesde, isLoading)
                Divider(color = bordaCampos, thickness = 0.5.dp)
                PerfilCampo("Plano atual", dados.plano, isLoading)
            }

            Spacer(modifier = Modifier.height(16.dp))

            PerfilCard(titulo = "Configurações") {
                PerfilAcao("Alterar senha") { }
                Divider(color = bordaCampos, thickness = 0.5.dp)
                PerfilAcao("Privacidade e dados") { }
                Divider(color = bordaCampos, thickness = 0.5.dp)
                PerfilAcao("Excluir conta", corLabel = corPerigo) { }
            }

            if (erro != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = erro,
                    color = corPerigo,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            }
        }
    }
}

@Composable
private fun PerfilCard(titulo: String, conteudo: @Composable ColumnScope.() -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        Text(
            text = titulo.uppercase(),
            color = corTextoSec,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.8.sp,
            modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = fundoCard)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) { conteudo() }
        }
    }
}

@Composable
private fun PerfilCampo(label: String, valor: String, isLoading: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, color = corTextoSec, fontSize = 13.sp, modifier = Modifier.weight(1f))
        if (isLoading) {
            PerfilLoadingPlaceholder(width = 80.dp, height = 13.dp)
        } else {
            Text(text = valor, color = corTexto, fontSize = 13.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
private fun PerfilAcao(label: String, corLabel: Color = corTexto, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, color = corLabel, fontSize = 14.sp, modifier = Modifier.weight(1f))
        Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = corTextoSec, modifier = Modifier.size(18.dp))
    }
}

@Composable
private fun PerfilLoadingPlaceholder(width: androidx.compose.ui.unit.Dp, height: androidx.compose.ui.unit.Dp) {
    Box(
        modifier = Modifier
            .width(width)
            .height(height)
            .clip(RoundedCornerShape(6.dp))
            .background(fundoCampos)
    )
}