package com.example.plaintext.ui.screens.preferences

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.plaintext.ui.screens.login.TopBarComponent
import com.example.plaintext.ui.screens.util.PreferenceInput
import com.example.plaintext.ui.screens.util.PreferenceItem
import com.example.plaintext.ui.viewmodel.PreferencesState
import com.example.plaintext.ui.viewmodel.PreferencesViewModel

@Composable
fun SettingsScreen(
    viewModel: PreferencesViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopBarComponent()
        }
    ) { padding ->
        SettingsContent(
            modifier = Modifier.padding(padding),
            preferencesState = viewModel.preferencesState,
            updateLogin = viewModel::updateLogin,
            updatePassword = viewModel::updatePassword,
            updatePreencher = viewModel::updatePreencher
        )
    }
}

@Composable
fun SettingsContent(
    modifier: Modifier = Modifier,
    preferencesState: PreferencesState,
    updateLogin: (String) -> Unit,
    updatePassword: (String) -> Unit,
    updatePreencher: (Boolean) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        PreferenceInput(
            title = "Preencher Login",
            label = "Login",
            fieldValue = preferencesState.login,
            summary = "Preencher login na tela inicial",
            onFinish = updateLogin
        )

        PreferenceInput(
            title = "Setar Senha",
            label = "Senha",
            fieldValue = preferencesState.password,
            summary = "Senha para entrar no sistema",
            onFinish = updatePassword
        )

        PreferenceItem(
            title = "Preencher Login",
            summary = "Preencher login na tela inicial",
            onClick = {
                updatePreencher(!preferencesState.preencher)
            },
            control = {
                Switch(
                    checked = preferencesState.preencher,
                    onCheckedChange = updatePreencher
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsContent(
        preferencesState = PreferencesState(),
        updateLogin = {},
        updatePassword = {},
        updatePreencher = {}
    )
}
