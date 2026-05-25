package com.example.mymoneyandroid.model

data class Mensagem(
    val idCategoria: Int? = null,
    val idUsuario: Int? = null,
    val assunto: String,
    val mensagem: String
)
