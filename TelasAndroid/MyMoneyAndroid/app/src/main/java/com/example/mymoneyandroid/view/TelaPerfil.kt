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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mymoneyandroid.viewmodel.PerfilViewModel

// Cores usadas na tela
private val fundoTela = Color(0xFF0F0F0F)
private val fundoCard = Color(0xFF1C1C1E)
private val fundoCampos = Color(0xFF2C2C2E)
private val bordaCampos = Color(0xFF3A3A3C)
private val corTexto = Color(0xFFFFFFFF)
private val corTextoCard = Color(0xFF8E8E93)
private val fundoBotao = Color(0xFF34C759)
private val corExcluirConta = Color(0xFFFF453A)

data class PerfilUsuario(
    val nome: String = "",
    val cpf: String = "",
    val email: String = "",
    val dataCriacao: String = ""
)

@Composable
fun PerfilScreen(
    controleNavegacao: NavController,
    viewModel: PerfilViewModel = viewModel(),
    idUsuario: Int
) {

    // Esse bloco vai rodar assim que a tela abre. Faz o ViewModel buscar o id correto
    LaunchedEffect(idUsuario) {
        viewModel.carregarPerfilDoUsuario(idUsuario)
    }

    // 4. Conectamos nossa UI diretamente com as variáveis do seu ViewModel
    val carregando = viewModel.carregando
    val erro = viewModel.mensagemErro
    val dados by viewModel.perfil.collectAsState()

    // 5. Tratamento amigável: se os dados ainda não chegaram da internet, deixamos strings vazias
    val nomeExibicao = dados?.nome ?: ""
    val cpfExibicao = dados?.cpf ?: ""
    val emailExibicao = dados?.email ?: ""

    MenuScreen(tituloDaPagina = "", controleNagegacao = controleNavegacao) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(fundoTela)
                .verticalScroll(rememberScrollState())
                .padding(bottom = 32.dp)
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            // Cabeçalho do Perfil Centralizado
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if (!carregando && nomeExibicao.isNotBlank()) {

                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .clip(CircleShape)
                            .background(fundoBotao),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = nomeExibicao
                                .split(" ")
                                .take(2)
                                .mapNotNull { it.firstOrNull()?.uppercaseChar() }
                                .joinToString(""),
                            color = corTexto,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Black
                        )
                    }

                } else if (carregando) {

                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .clip(CircleShape)
                            .background(fundoCampos)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (carregando) {

                    Box(
                        modifier = Modifier
                            .width(160.dp)
                            .height(24.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(fundoCampos)
                    )

                } else {

                    Text(
                        text = nomeExibicao,
                        color = corTexto,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Bloco de Informações
            PerfilCard(titulo = "Informações pessoais") {

                PerfilCampo("Nome completo", nomeExibicao, carregando)

                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    color = bordaCampos,
                    thickness = 0.5.dp
                )

                PerfilCampo("E-mail", emailExibicao, carregando)

                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    color = bordaCampos,
                    thickness = 0.5.dp
                )

                PerfilCampo("CPF", cpfExibicao, carregando)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Bloco de Configurações
            PerfilCard(titulo = "Segurança e Ajustes") {

                PerfilAcao("Alterar senha") { }

                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    color = bordaCampos,
                    thickness = 0.5.dp
                )

                PerfilAcao("Privacidade e dados") { }

                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    color = bordaCampos,
                    thickness = 0.5.dp
                )

                PerfilAcao("Excluir minha conta", corLabel = corExcluirConta) { }
            }

            if (erro != null) {

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = erro,
                    color = corExcluirConta,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(horizontal = 30.dp),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun PerfilCard(
    titulo: String,
    conteudo: @Composable ColumnScope.() -> Unit
) {

    Column(modifier = Modifier.padding(horizontal = 20.dp)) {

        Text(
            text = titulo.uppercase(),
            color = corTextoCard,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = fundoCard)
        ) {

            Column(modifier = Modifier.fillMaxWidth()) {
                conteudo()
            }
        }
    }
}

@Composable
private fun PerfilCampo(
    label: String,
    valor: String,
    carregando: Boolean = false
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = label,
            color = corTextoCard,
            fontSize = 14.sp,
            modifier = Modifier.weight(1f)
        )

        if (carregando) {

            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(14.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(fundoCampos)
            )

        } else {

            Text(
                text = valor,
                color = corTexto,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun PerfilAcao(
    label: String,
    corLabel: Color = corTexto,
    aoClicar: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { aoClicar() }
            .padding(horizontal = 20.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = label,
            color = corLabel,
            fontSize = 15.sp,
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.Medium
        )

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = corTextoCard,
            modifier = Modifier.size(20.dp)
        )
    }
}