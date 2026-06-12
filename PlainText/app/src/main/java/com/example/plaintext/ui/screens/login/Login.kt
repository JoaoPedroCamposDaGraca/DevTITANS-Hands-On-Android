package com.example.plaintext.ui.screens.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.plaintext.R
import com.example.plaintext.ui.viewmodel.PreferencesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


data class LoginState(
    val preencher: Boolean,
    val login: String,
    val navigateToSettings: () -> Unit,
    val navigateToList: (name: String) -> Unit,
    val checkCredentials: (login: String, password: String) -> Boolean,
)

data class LoginUiState(
    val login: String = "",
    val password: String = "",
    val preencher: Boolean = false,
    val saveLogin: Boolean = false
)

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    var loginState by mutableStateOf(LoginUiState())
        private set

    fun updateLogin(login: String) {
        loginState = loginState.copy(login = login)
    }

    fun updatePassword(password: String) {
        loginState = loginState.copy(password = password)
    }

    fun updateSaveLogin(saveLogin: Boolean) {
        loginState = loginState.copy(saveLogin = saveLogin)
    }

    fun updatePreencher(preencher: Boolean) {
        loginState = loginState.copy(preencher = preencher)
    }

    fun checkCredentials(preencher: Boolean){
     loginState = loginState.copy(preencher=preencher)
    }
    fun checkCredentials(
        correctLogin: String,
        correctPassword: String,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ){
        if (loginState.login == correctLogin && loginState.password == correctPassword){
            onSuccess()
        }else{
            onError()
        }
    }
}
@Composable
fun Login_screen(
    navigateToSettings: () -> Unit,
    navigateToList: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
    preferencesViewModel: PreferencesViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    LoginContent(
        navigateToSettings = navigateToSettings,
        navigateToList = navigateToList,
        loginState = viewModel.loginState,
        updateLogin = viewModel::updateLogin,
        updatePassword = viewModel::updatePassword,
        updateSaveLogin = viewModel::updateSaveLogin,
        onEnviar = {
            viewModel.checkCredentials(
                correctLogin = preferencesViewModel.preferencesState.login,
                correctPassword = preferencesViewModel.preferencesState.password,
                onSuccess = { navigateToList() },
                onError = {
                    Toast.makeText(context, "Login/Senha inválidos", Toast.LENGTH_SHORT).show()
                }
            )
        }
    )
}

val backgroundColor = Color(0xFF1E0B00)
val bannerColor = Color(0xFF9CC137)
val textColor = Color(0xFFE8D7C8)
val borderColor = Color(0xFFD7B49A)
val buttonColor = Color(0xFFF6BE9A)

@Composable
private fun LoginContent(
    navigateToSettings: () -> Unit,
    navigateToList: () -> Unit,
    loginState: LoginUiState,
    updateLogin: (String) -> Unit,
    updatePassword: (String) -> Unit,
    updateSaveLogin: (Boolean) -> Unit,
    onEnviar: () -> Unit
) {
    Scaffold(
        containerColor = backgroundColor,
        topBar = {
            TopBarComponent(
                navigateToSettings = navigateToSettings,
                navigateToSensores = {}
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor)
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(bannerColor)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Android Logo",
                    modifier = Modifier.width(80.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = "\"The most\nsecure password\nmanager\"",
                        color = Color.White,
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Bob and Alice",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Digite suas credenciais para continuar",
                color = textColor,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Login:",
                    color = textColor,
                    modifier = Modifier.width(80.dp)
                )

                OutlinedTextField(
                    value = loginState.login,
                    onValueChange = updateLogin,
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = borderColor,
                        unfocusedBorderColor = borderColor,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = Color.White
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Senha:",
                    color = textColor,
                    modifier = Modifier.width(80.dp)
                )

                OutlinedTextField(
                    value = loginState.password,
                    onValueChange = updatePassword,
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = borderColor,
                        unfocusedBorderColor = borderColor,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = Color.White
                    )
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = loginState.saveLogin,
                    onCheckedChange = updateSaveLogin
                )

                Text(
                    text = "Salvar as informações de login",
                    color = textColor
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onEnviar,
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonColor
                )
            ) {
                Text(
                    text = "Enviar",
                    color = backgroundColor
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        LoginContent(
            navigateToSettings = {},
            navigateToList = {},
            loginState = LoginUiState(),
            updateLogin = {},
            updatePassword = {},
            updateSaveLogin = {},
            onEnviar = {}
        )
    }
}

@Composable
fun MyAlertDialog(shouldShowDialog: MutableState<Boolean>) {
    if (shouldShowDialog.value) {
        AlertDialog(
            onDismissRequest = {
                shouldShowDialog.value = false
            },

            title = { Text(text = "Sobre") },
            text = { Text(text = "PlainText Password Manager v1.0") },
            confirmButton = {
                Button(
                    onClick = { shouldShowDialog.value = false }
                ) {
                    Text(text = "Ok")
                }
            }
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBarComponent(
    navigateToSettings: (() -> Unit?)? = null,
    navigateToSensores: (() -> Unit?)? = null,
) {
    var expanded by remember { mutableStateOf(false) }
    val shouldShowDialog = remember { mutableStateOf(false) }

    if (shouldShowDialog.value) {
        MyAlertDialog(shouldShowDialog = shouldShowDialog)
    }

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = backgroundColor,
            titleContentColor = textColor,
            actionIconContentColor = textColor
        ),
        title = { Text("PlainText") },
        actions = {
            if (navigateToSettings != null && navigateToSensores != null) {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Configurações") },
                        onClick = {
                            navigateToSettings();
                            expanded = false;
                        },
                        modifier = Modifier.padding(8.dp)
                    )
                    DropdownMenuItem(
                        text = {
                            Text("Sobre");
                        },
                        onClick = {
                            shouldShowDialog.value = true;
                            expanded = false;
                        },
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    )
}
