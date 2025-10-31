package com.example.mytradingapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun LogRegScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
){
    Scaffold (
        topBar = {MyLogRegTopBar(onBackClick = onBackClick) }
    ){innerPadding ->
        Column(modifier = modifier.padding(paddingValues = innerPadding)){
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