package org.lotka.xenon.presentation.ui.navigation

sealed class ScreensNavigation(val route: String) {




    object ExploreScreen : ScreensNavigation(route = "explore_screen")
    object LoginScreen : ScreensNavigation(route = "login_screen")
    object RegisterScreen : ScreensNavigation(route = "register_screen")
    object MyCardScreen : ScreensNavigation(route = "card_screen")
    object SeeAllScreen : ScreensNavigation(route = "see_all_screen")
    object DetailScreen : ScreensNavigation(route = "detail_screen")
    object MyOrderScreen : ScreensNavigation(route = "order_screen")
    object ProfileScreen : ScreensNavigation(route = "profile")
    object WishList : ScreensNavigation(route = "wish_list")
    object SearchScreen : ScreensNavigation(route = "search_screen")
    object EditProfileScreen : ScreensNavigation(route = "edit_profile_screen")





}