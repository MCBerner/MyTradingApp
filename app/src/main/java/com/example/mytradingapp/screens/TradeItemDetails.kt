package com.example.mytradingapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mytradingapp.model.TradeItem
import com.example.mytradingapp.utilities.ToDateTimeString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TradeItemDetails(
    tradeItem: TradeItem,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    Scaffold(
        topBar = { MyTradeItemsDetailsBar(onBackClick = onBackClick) }
    ) { innerPadding ->
        if (tradeItem == null) {
            Text("Item not found or loading...", modifier = Modifier.padding(innerPadding))
        } else {
            Column(
                modifier = modifier
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF5AB4B4))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Description:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Text(tradeItem.description, fontSize = 18.sp)
                        Text(
                            "Price:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                        Text(tradeItem.price.toString(), fontSize = 18.sp)
                        Text(
                            "Seller Email:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                        Text(text = tradeItem.sellerEmail, fontSize = 18.sp)
                        Text(
                            "Seller Phone",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                        Text(tradeItem.sellerPhone.toString(), fontSize = 18.sp)
                        Text(
                            "Timestamp:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                        Text(tradeItem.time.ToDateTimeString(), fontSize = 18.sp)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTradeItemsDetailsBar(onBackClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = "Trade item details",
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        }
    )
}