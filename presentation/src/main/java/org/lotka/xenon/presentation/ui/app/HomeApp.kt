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
import org.lotka.xenon.presentation.screen.auth.login.LoginScreen
import org.lotka.xenon.presentation.screen.my_order.MyOrderScreen
import org.lotka.xenon.presentation.screen.detail.DetailScreen
import org.lotka.xenon.presentation.screen.explore.ExploreScreen
import org.lotka.xenon.presentation.screen.see_all.SeeAllScreen
import org.lotka.xenon.presentation.screen.my_card.MyCardScreen

import org.lotka.xenon.presentation.screen.search.SearchScreen
import org.lotka.xenon.presentation.screen.wish_list.WishListScreen


import org.lotka.xenon.presentation.ui.navigation.ScreensNavigation
import org.lotka.xenon.presentation.screen.profile.ProfileScreen
import org.lotka.xenon.presentation.screen.edit_profile.EditProfileScreen
import org.lotka.xenon.presentation.screen.auth.register.RegisterScreen


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
                startDestination = ScreensNavigation.LoginScreen.route,
            ) {
                composable(
                    route = ScreensNavigation.LoginScreen.route,
                ) {
                    LoginScreen(

                        onNavigateTo = navController::navigate,
                        onNavigateUp = navController::navigateUp
                    )

                }

                composable(
                    route = ScreensNavigation.RegisterScreen.route,
                ) {
                    RegisterScreen(
                        onNavigateTo = navController::navigate,
                        onNavigateUp = navController::navigateUp
                    )

                }


                composable(
                    route = ScreensNavigation.ExploreScreen.route,
                ) {
                    ExploreScreen(
                        onNavigateToDetail = navController::navigate,
                        onNavigateToSeeAll = navController::navigate,
                        onNavigateToSearch = navController::navigate,
                    )


                }
                composable(
                    route = ScreensNavigation.MyOrderScreen.route,
                ) {
                    MyOrderScreen(onNavigateUp = navController::navigateUp)

                }
                composable(
                    route = ScreensNavigation.SearchScreen.route,
                ) {
                    SearchScreen(
                        onNavigateUp = navController::navigateUp,
                        onNavigateToDetail = navController::navigate
                    )

                }

                composable(
                    route = ScreensNavigation.WishList.route,
                ) {
                    WishListScreen(onNavigateUp = navController::navigateUp)

                }

                composable(
                    route = ScreensNavigation.ProfileScreen.route,
                ) {
                    ProfileScreen(
                        onNavigateToLogin = navController::navigate,
                        navigateToEditProfile = navController::navigate)

                }
                composable(
                    route = ScreensNavigation.EditProfileScreen.route,
//                            + "/{userId}"
//                    arguments = listOf(navArgument("userId")
//                    { type = NavType.StringType }),
                ) {
                    EditProfileScreen(onNavigateUp = navController::navigateUp)

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
                    DetailScreen(onNavigateUp = navController::navigateUp)
                }


                composable(
                    route = ScreensNavigation.MyCardScreen.route,
                ) {
                    MyCardScreen(
                        onNavigateUp = navController::navigateUp
                    )


                }


            }
        })
}






