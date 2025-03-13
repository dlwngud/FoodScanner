package com.wngud.foodscanner.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.wngud.foodscanner.ui.screen.foodInventory.FoodInventoryScreen
import com.wngud.foodscanner.ui.screen.myPage.MyPageScreen
import com.wngud.foodscanner.ui.screen.scanner.ScannerScreen

@Composable
fun FoodScannerNavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Screen.FoodInventoryScreen.route) {
        composable(Screen.ScannerScreen.route) {
            ScannerScreen()
        }
        composable(Screen.FoodInventoryScreen.route) {
            FoodInventoryScreen()
        }
        composable(Screen.MyPageScreen.route) {
            MyPageScreen()
        }
    }
}