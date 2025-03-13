package com.wngud.foodscanner.ui.screen.scanner

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ScannerScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text("Scanner Screen", modifier = Modifier.align(Alignment.Center))
    }
}