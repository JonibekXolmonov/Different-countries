package com.find.differentcountries.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.find.differentcountries.ui.screen.game.GameScreen
import com.find.differentcountries.ui.screen.menu.MenuScreen
import com.find.differentcountries.ui.screen.rules.RulesScreen
import com.find.differentcountries.ui.screen.score.ScoreScreen
import com.find.differentcountries.ui.screen.settings.SettingsScreen
import com.find.differentcountries.ui.utils.Screen

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Menu.route,
    onVibrate: () -> Unit,
    onSound: (Boolean) -> Unit
) {
    NavHost(
        navController,
        startDestination = startDestination,
    ) {
        composable(Screen.Menu.route) {
            MenuScreen(
                onGameStart = {
                    onVibrate()
                    navController.navigate(Screen.Game.route)
                },
                onSettingsClick = {
                    onVibrate()
                    navController.navigate(Screen.Settings.route)
                },
                onRulesClick = {
                    onVibrate()
                    navController.navigate(Screen.Rules.route)
                }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                onBackPressed = {
                    navController.popBackStack()
                    onVibrate()
                }
            )
        }

        composable(Screen.Game.route) {
            GameScreen(
                onBack = {
                    onVibrate()
                    navController.popBackStack()
                },
                onVibrate = onVibrate,
            )
        }

        composable(Screen.Rules.route) {
            RulesScreen(
                onBack = {
                    onVibrate()
                    navController.popBackStack()
                },
                onVibrate = onVibrate
            )
        }
    }
}