package com.example.mymoneyandroid.model

data class CadastroUsuario(
    val Nome: String,
    val Cpf: String,
    val Email: String,
    val Senha: String
)

data class DadosUsuario(
    val nome: String,
    val email: String,
    val cpf: String
)

data class LoginUsuario(
    val email: String,
    val senha: String
)