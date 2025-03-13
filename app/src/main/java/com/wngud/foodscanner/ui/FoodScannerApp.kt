package com.wngud.foodscanner.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.wngud.foodscanner.navigation.FoodScannerNavGraph
import com.wngud.foodscanner.ui.screen.component.BottomNavigationBar
import com.wngud.foodscanner.ui.theme.FoodScannerTheme

@Composable
fun FoodScannerApp() {
    FoodScannerTheme {
        val navController = rememberNavController()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomNavigationBar(navController)
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = it.calculateBottomPadding())
            ) {
                FoodScannerNavGraph(navController = navController)
            }
        }
    }
}