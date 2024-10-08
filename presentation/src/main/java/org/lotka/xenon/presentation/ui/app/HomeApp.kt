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
import org.lotka.xenon.presentation.screen.card.CardScreen
import org.lotka.xenon.presentation.screen.detail.DetailScreen
import org.lotka.xenon.presentation.screen.explore.ExploreScreen
import org.lotka.xenon.presentation.screen.see_all.SeeAllScreen
import org.lotka.xenon.presentation.screen.my_order.MyOrderScreen
import org.lotka.xenon.presentation.screen.profile.ProfileScreen
import org.lotka.xenon.presentation.screen.wish_list.WishListScreen


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
                startDestination = ScreensNavigation.ExploreScreen.route,
            ) {
                composable(
                    route = ScreensNavigation.ExploreScreen.route,
                ) {
                    ExploreScreen(
                        onNavigateToDetail = navController::navigate,
                        onNavigateToSeeAll = navController::navigate
                    )


                }
                composable(
                    route = ScreensNavigation.CardScreen.route,
                ) {
                    CardScreen()

                }

                composable(
                    route = ScreensNavigation.WishList.route,
                ) {
                    WishListScreen()

                }

                composable(
                    route = ScreensNavigation.Profile.route,
                ) {
                    ProfileScreen()

                }


                composable(
                    route = ScreensNavigation.SeeAllScreen.route + "/{categoryId}",
                    arguments = listOf(navArgument("categoryId")
                    { type = NavType.StringType })  // Pass categoryId
                ) {
                    // Get categoryId

                    SeeAllScreen(
                        selectedCatgory = it.arguments?.getString("categoryId") ?: "1",
                        onNavigateUp = navController::navigateUp
                    )


                }
                composable(
                    route = ScreensNavigation.DetailScreen.route + "/{itemId}",
                    arguments = listOf(navArgument("itemId")
                    { type = NavType.StringType })
                ) {
                    DetailScreen(
                        onNavigateUp = navController::navigateUp
                    )


                }
                composable(
                    route = ScreensNavigation.MyOrder.route,
                ) {
                    MyOrderScreen(
                        onNavigateUp = navController::navigateUp
                    )


                }


            }
        })
}






