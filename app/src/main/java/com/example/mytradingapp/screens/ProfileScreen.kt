package com.example.mytradingapp.screens

import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    items: List<String> = emptyList(),
    onAddItem: (String) -> Unit = {},
    onDeleteItem: (String) -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    var newItem by remember { mutableStateOf("") }

    Scaffold (
        topBar = {MyProfileTopBar(onBackClick = onBackClick) }
    ) { innerPadding ->
        Column(modifier = modifier.padding(paddingValues = innerPadding)) {
            Row(verticalAlignment = CenterVertically) {
                var inputError by remember { mutableStateOf(false) }
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = newItem,
                    label = { Text("New Item") },
                    onValueChange = { newItem = it },
                    supportingText = {
                        if (inputError)
                            Text("Der er ikke indtastet en ny vare.", color = Color.Black, fontSize = 16.sp)
                    }
                )
                Button(onClick = {
                    if (newItem.isBlank()) {
                        inputError = true
                    } else {
                        inputError = false
                        onAddItem(newItem)
                        newItem = ""
                    }
                }
                ) {
                    Icon(Icons.Outlined.Add, contentDescription = "Add")
                }
            }
            LazyColumn {
                items(items) { item ->
                    ItemsCard(item = item) { onDeleteItem(item) }
                }
            }
        }
    }
}

@Composable
fun ItemsCard(
    item: String, modifier: Modifier = Modifier,
    onDelete: () -> Unit = {},
) {
    var showDialog by remember { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(red = 40, green = 180, blue = 160),
            contentColor = Color.White
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Row(
            modifier = Modifier.padding(2.dp),
            verticalAlignment = CenterVertically
        ) {
            Text(text = item, modifier = Modifier.padding(8.dp))
            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = { showDialog = true }) {
                Icon(Icons.Outlined.Delete, contentDescription = "Delete")
            }
        }
    }
    if (showDialog) {
        AlertDialogExample(
            onDismissRequest = { showDialog = false },
            onConfirmation = {
                onDelete()
                showDialog = false },
            dialogTitle = "Delete?",
            dialogText = "Are you sure?",
            icon = Icons.Outlined.Delete
        )
    }
}

@Composable
fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,   // bare en funktionstype
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = { Icon(icon, contentDescription = "Example Icon") },
        title = { Text(text = dialogTitle) },
        text = { Text(text = dialogText) },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = onConfirmation) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Dismiss")
            }
        }
    )
}
@Suppress("DEPRECATION")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProfileTopBar(onBackClick: () -> Unit ) {
    TopAppBar(
        title = {
            Text(
                text = "My profile",
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
