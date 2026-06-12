package com.example.plaintext.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class PreferencesState(
    val login: String = DEFAULT_LOGIN,
    val password: String = DEFAULT_PASSWORD,
    val preencher: Boolean = DEFAULT_PREENCHER
)

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    var preferencesState by mutableStateOf(
        PreferencesState(
            login = savedStateHandle[LOGIN_KEY] ?: DEFAULT_LOGIN,
            password = savedStateHandle[PASSWORD_KEY] ?: DEFAULT_PASSWORD,
            preencher = savedStateHandle[PREENCHER_KEY] ?: DEFAULT_PREENCHER
        )
    )
        private set

    fun updateLogin(login: String) {
        updateState(preferencesState.copy(login = login))
    }

    fun updatePassword(password: String) {
        updateState(preferencesState.copy(password = password))
    }

    fun updatePreencher(preencher: Boolean) {
        updateState(preferencesState.copy(preencher = preencher))
    }

    fun checkCredentials(login: String, password: String): Boolean {
        return login == preferencesState.login && password == preferencesState.password
    }

    private fun updateState(state: PreferencesState) {
        preferencesState = state
        savedStateHandle[LOGIN_KEY] = state.login
        savedStateHandle[PASSWORD_KEY] = state.password
        savedStateHandle[PREENCHER_KEY] = state.preencher
    }
}

private const val LOGIN_KEY = "login"
private const val PASSWORD_KEY = "password"
private const val PREENCHER_KEY = "preencher"
private const val DEFAULT_LOGIN = "devtitans"
private const val DEFAULT_PASSWORD = "123"
private const val DEFAULT_PREENCHER = true