package com.wngud.foodscanner.ui.screen.foodInventory

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun FoodInventoryScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text("FoodInventory Screen", modifier = Modifier.align(Alignment.Center))
    }
}