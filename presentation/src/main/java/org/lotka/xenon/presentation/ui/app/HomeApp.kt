package org.lotka.xenon.presentation.ui.app


import android.annotation.SuppressLint

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import org.lotka.xenon.presentation.screen.detail.DetailScreen
import org.lotka.xenon.presentation.screen.home.HomeScreen
import org.lotka.xenon.presentation.screen.see_all.SeeAllScreen
import org.lotka.xenon.presentation.screen.shop.ShopScreen


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
            NavHost(
                navController = navController,
                startDestination = ScreensNavigation.DetailScreen.route,
            ) {
                composable(
                    route = ScreensNavigation.HomeScreen.route,
                ) {
                    HomeScreen(
                        onNavigateToSeeAll = navController::navigate
                    )


                }
                composable(
                    route = ScreensNavigation.SeeAllScreen.route + "/{categoryId}",
                    arguments = listOf(navArgument("categoryId") { type = NavType.IntType })  // Pass categoryId
                ) { backStackEntry ->
                    val categoryId = backStackEntry.arguments?.getInt("categoryId") ?: 0  // Get categoryId
                    categoryId?.let {
                    SeeAllScreen(
                        categoryId = it
                        ,onNavigateUp = navController::navigateUp)

                    }


                }
                composable(
                    route = ScreensNavigation.DetailScreen.route,
                ) {
                    DetailScreen()


                }
                composable(
                    route = ScreensNavigation.ShopScreen.route,
                ) {
                    ShopScreen()


                }


            }
        })
}






