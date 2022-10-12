package com.company.jetweatherforecast.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.company.jetweatherforecast.uiLayer.screens.main.MainScreen
import com.company.jetweatherforecast.domainLayer.components.MainViewModel
import com.company.jetweatherforecast.uiLayer.screens.about.AboutScreen
import com.company.jetweatherforecast.uiLayer.screens.favorites.FavoriteScreen
import com.company.jetweatherforecast.uiLayer.screens.search.SearchScreen
import com.company.jetweatherforecast.uiLayer.screens.settings.SettingsScreen
import com.company.jetweatherforecast.uiLayer.screens.splash.WeatherSplashScreen

@ExperimentalComposeUiApi
@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = WeatherScreens.SplashScreen.name) {
        composable(WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController = navController)
        }
        composable("${WeatherScreens.MainScreen.name}/{city}",
            arguments = listOf(navArgument(name = "city"){
                type = NavType.StringType
            })) {
            it.arguments?.getString("city").let { city ->
                val mainViewModel = hiltViewModel<MainViewModel>()
                MainScreen(
                    navController = navController,
                    mainViewModel = mainViewModel,
                    city = city)
            }

        }
        composable(WeatherScreens.SearchScreen.name) {
            SearchScreen(navController = navController)
        }
        composable(WeatherScreens.AboutScreen.name) {
            AboutScreen(navController = navController)
        }
        composable(WeatherScreens.FavoriteScreen.name) {
            FavoriteScreen(navController = navController)
        }
        composable(WeatherScreens.SettingsScreen.name) {
            SettingsScreen(navController = navController)
        }
    }
}