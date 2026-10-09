package com.example.ilearning.ui.screens.login

import android.content.Context

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ilearning.R
import com.example.ilearning.data.remote.Response
import com.example.ilearning.ui.theme.Purple40
import kotlinx.coroutines.launch
import kotlin.math.sin
import androidx.core.content.edit


@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var isVisible by rememberSaveable { mutableStateOf(false) }

    val loginState by loginViewModel.loginState
        .collectAsStateWithLifecycle()

    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val isLoading = loginState is Response.Loading

    LaunchedEffect(loginState) {
        when (val state = loginState) {
            is Response.Success -> {
                onLoginSuccess()
            }

            is Response.Error -> {
                snackBarHostState.showSnackbar(state.message)
            }

            is Response.Loading -> Unit

            Response.Empty -> Unit
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                val (txtLogo, txtLogin, edEmail, edPass, btnSubmit) =
                    createRefs()

                Text(
                    modifier = Modifier.constrainAs(txtLogo) {
                        top.linkTo(parent.top, margin = 100.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                    text = "iLearning",
                    style = TextStyle(
                        color = Purple40,
                        fontSize = 30.sp, fontFamily = FontFamily(Font(R.font.google_sans_bold)),
                    ),
                )

                Text(
                    modifier = Modifier.constrainAs(txtLogin) {
                        top.linkTo(txtLogo.bottom, margin = 30.dp)
                        start.linkTo(parent.start, margin = 24.dp)
                    },
                    text = "Login",
                    style = TextStyle(
                        fontSize = 24.sp, fontFamily = FontFamily(Font(R.font.google_sans_bold)),
                    ),
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .constrainAs(edEmail) {
                            top.linkTo(txtLogin.bottom, margin = 18.dp)
                        },
                    value = email,
                    onValueChange = { email = it },
                    enabled = !isLoading,
                    singleLine = true,
                    label = { Text("Email") },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Email,
                            contentDescription = null
                        )
                    }
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .constrainAs(edPass) {
                            top.linkTo(edEmail.bottom, margin = 18.dp)
                        },
                    value = password,
                    onValueChange = { password = it },
                    enabled = !isLoading,
                    singleLine = true,
                    label = { Text("Password") },
                    visualTransformation = if (isVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Lock,
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = { isVisible = !isVisible }
                        ) {
                            Icon(
                                imageVector = if (isVisible) {
                                    Icons.Default.Visibility
                                } else {
                                    Icons.Default.VisibilityOff
                                },
                                contentDescription = if (isVisible) {
                                    "Hide password"
                                } else {
                                    "Show password"
                                }
                            )
                        }
                    }
                )

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .constrainAs(btnSubmit) {
                            bottom.linkTo(parent.bottom)
                        },
                    enabled = !isLoading,
                    onClick = {
                        if (handleCredentials(email, password)) {
                            loginViewModel.login(
                                email.trim(),
                                password
                            )
                        } else {
                            scope.launch {
                                snackBarHostState.showSnackbar(
                                    "Please enter valid email and password"
                                )
                            }
                        }
                    }
                ) {
                    Text(
                        if (isLoading) "Signing in..." else "Submit"
                    )
                }
            }

            if (isLoading) {
                ProgressBar()
            }
        }
    }
}


fun handleCredentials(email: String, pass: String): Boolean {
    return (email.isNotBlank() && pass.isNotBlank())
}



@Composable
fun ProgressBar() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        awaitPointerEvent()
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
