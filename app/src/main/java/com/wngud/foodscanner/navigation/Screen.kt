package com.wngud.foodscanner.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.wngud.foodscanner.R

sealed class Screen(
    val route: String,
    @StringRes val name: Int,
    @DrawableRes val icon: Int
) {
    object FoodInventoryScreen : Screen("foodInventoryScreen", R.string.food_inventory, R.drawable.foodinventory)
    object ScannerScreen : Screen("scannerScreen", R.string.scanner, R.drawable.scanner)
    object MyPageScreen : Screen("myPageScreen", R.string.my_page, R.drawable.mypage)
}