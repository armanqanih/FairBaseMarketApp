package org.lotka.xenon.presentation.ui.navigation

sealed class ScreensNavigation(val route: String) {




    object ExploreScreen : ScreensNavigation(route = "explore_screen")
    object MyOrder : ScreensNavigation(route = "shop_screen")
    object SeeAllScreen : ScreensNavigation(route = "see_all_screen")
    object DetailScreen : ScreensNavigation(route = "detail_screen")
    object CardScreen : ScreensNavigation(route = "card_screen")
    object Profile : ScreensNavigation(route = "profile")
    object WishList : ScreensNavigation(route = "wish_list")





}