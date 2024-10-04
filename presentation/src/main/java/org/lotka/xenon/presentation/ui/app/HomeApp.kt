package org.lotka.xenon.presentation.ui.app




import android.annotation.SuppressLint

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import org.lotka.xenon.presentation.screen.home.HomeScreen


import org.lotka.xenon.presentation.ui.navigation.ScreensNavigation


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")

@Composable
fun HomeApp(
    activity: HomeActivity,
    navController: NavHostController,

    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    keyboardController: SoftwareKeyboardController,

    ) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val scaffoldState = rememberScaffoldState()




    Scaffold(

        content = { _ ->
            NavHost(navController = navController,
                startDestination = ScreensNavigation.HomeScreen .route,
          ) {
                composable(
                    route = ScreensNavigation.HomeScreen.route,
                ) {
             HomeScreen()


                }

             }})}






