package com.example.mymoneyandroid.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "perfilUsuario")

class SessaoManager(val context: Context) {

    companion object {

        val idUsuario =
            intPreferencesKey("idUsuario")
    }

    suspend fun salvarIdUsuario(id: Int) {

        context.dataStore.edit {

            it[idUsuario] = id
        }
    }

    fun obterUsuario() = context.dataStore.data.map {

        it[idUsuario]
    }

    suspend fun sairPrograma() {

        context.dataStore.edit {

            it.remove(idUsuario)
        }
    }
}