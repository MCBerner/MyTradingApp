package com.example.mytradingapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Add
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mytradingapp.model.TradeItem


@Composable
fun AddTradeItemScreen(
    modifier: Modifier = Modifier,
    sellerEmail: String,
    addTradeItem: (TradeItem) -> Unit = {},
    onDeleteItem: (String) -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    var description by remember { mutableStateOf("") }
    var priceStr by remember { mutableStateOf("") }
    var sellerPhone by remember { mutableStateOf("") }

    var descriptionIsError by remember { mutableStateOf(false) }
    var priceIsError by remember { mutableStateOf(false) }
    var sellerPhoneIsError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {MyAddTradeItemTopBar(onBackClick = onBackClick, ) }
    ) { innerPadding ->
        Column(modifier = modifier.padding(paddingValues = innerPadding)) {

            OutlinedTextField(
                value = description,
                onValueChange = {
                    description = it
                    descriptionIsError = false
                },
                label = { Text("New Item") },
                isError = descriptionIsError,
                modifier = Modifier.fillMaxWidth()
            )
            if (descriptionIsError) {
                Text(
                    "Der er ikke indtastet en ny vare.",
                    color = Color.Red,
                    fontSize = 14.sp
                )
            }

            OutlinedTextField(
                value = priceStr,
                onValueChange = {
                    priceStr = it
                    priceIsError = false
                },
                label = { Text("Price") },
                isError = priceIsError,
                modifier = Modifier.fillMaxWidth()
            )
            if (priceIsError) {
                Text(
                    "Der er ikke indtastet en gyldig pris.",
                    color = Color.Red,
                    fontSize = 14.sp
                )
            }

            OutlinedTextField(
                value = sellerPhone,
                onValueChange = {
                    sellerPhone = it
                    sellerPhoneIsError = false
                },
                label = { Text("Phone Number") },
                isError = sellerPhoneIsError,
                modifier = Modifier.fillMaxWidth()
            )
            if (sellerPhoneIsError) {
                Text(
                    "Der er ikke indtastet et gyldigt telefonnummer.",
                    color = Color.Red,
                    fontSize = 14.sp
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Button(onClick = {
                    var hasError = false
                    if (description.isEmpty()) { descriptionIsError = true; hasError = true }
                    val price = priceStr.toInt()
                    //if (price == null) { priceIsError = true; hasError = true }
                    if (sellerPhone.isEmpty()) { sellerPhoneIsError = true; hasError = true }

                    if (hasError) return@Button

                    val tradeItem = TradeItem(
                        description = description,
                        price = price,
                        sellerEmail = sellerEmail,
                        sellerPhone = sellerPhone
                    )
                  //  val tradeItem = TradeItem("Morten", 23.0, "chami@gmail.com", "12341234")
                    addTradeItem(tradeItem)
                    onBackClick()
                }) {
                    Icon(Icons.Outlined.Add, contentDescription = "Add")
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAddTradeItemTopBar(onBackClick: () -> Unit) {
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
/*@Composable
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
                showDialog = false
            },
            dialogTitle = "Delete?",
            dialogText = "Are you sure?",
            icon = Icons.Outlined.Delete
        )
    }
}
@Composable
fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
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
}*/
