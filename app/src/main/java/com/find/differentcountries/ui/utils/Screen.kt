package com.find.differentcountries.ui.utils

sealed class Screen(
    val route: String
) {
    object Menu : Screen("menu")
    object Settings : Screen("settings")
    object Game : Screen("game")
    object Rules : Screen("rules")
    object Score : Screen("score")
}