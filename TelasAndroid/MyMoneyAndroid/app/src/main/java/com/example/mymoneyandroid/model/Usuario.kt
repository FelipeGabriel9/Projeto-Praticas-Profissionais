package com.example.mymoneyandroid.model

data class CadastroUsuario(
    val Nome: String,
    val Email: String,
    val Cpf: String,
    val Senha: String
)

data class DadosUsuario(
    val idUsuario: Int,
    val nome: String,
    val email: String
)

data class LoginUsuario(
    val email: String,
    val senha: String
)