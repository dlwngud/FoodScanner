package com.wngud.foodscanner.ui.screen.myPage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun MyPageScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text("MyPage Screen", modifier = Modifier.align(Alignment.Center))
    }
}