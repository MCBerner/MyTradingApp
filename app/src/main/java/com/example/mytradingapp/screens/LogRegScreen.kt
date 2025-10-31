package com.example.mytradingapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseUser

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogRegScreen(
    user: FirebaseUser? = null,
    message: String? = null,
    signIn: (email: String, password: String) -> Unit = { _, _ -> },
    register: (email: String, password: String) -> Unit = { _, _ -> },
    onBackClick: () -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailIsError by remember { mutableStateOf(false) }
    var passwordIsError by remember { mutableStateOf(false) }
    var showPassword by remember { mutableStateOf(false) }


    Scaffold(
        topBar = { MyLogRegTopBar(onBackClick = onBackClick) }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            if (user != null) {
                Text(text = "Welcome ${user.email ?: "unknown"}")
            }
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                isError = emailIsError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
            )
            if (emailIsError) {
                Text(text = "Invalid email")
            }
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation =
                    if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                isError = passwordIsError,
                trailingIcon = {
                    IconButton(onClick = { showPassword = !showPassword }) {
                        if (showPassword) {
                            Icon(Icons.Filled.Visibility, contentDescription = "Hide password")
                        } else {
                            Icon(Icons.Filled.VisibilityOff, contentDescription = "Show password")
                        }
                    }
                }
            )
            if (passwordIsError) {
                Text(text = "Invalid password")
            }
            if (message != null && message.isNotEmpty()) {
                Text(text = message)
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        signIn(email, password)
                    } else {
                        emailIsError = email.isEmpty()
                        passwordIsError = password.isEmpty()
                    }
                }
                ) {
                    Text(text = "Login")
                }
                Button(onClick = { register(email, password)

                }
                ) {
                    Text(text = "Register")
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyLogRegTopBar(onBackClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = "Login and register",
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },

        )
}
