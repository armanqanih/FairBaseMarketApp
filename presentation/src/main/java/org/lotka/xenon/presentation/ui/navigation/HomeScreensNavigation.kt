package org.lotka.xenon.presentation.ui.navigation

sealed class ScreensNavigation(val route: String) {




    object HomeScreen : ScreensNavigation(route = "home_screen")
    object ShopScreen : ScreensNavigation(route = "shop_screen")
    object SeeAllScreen : ScreensNavigation(route = "see_all_screen")
    object DetailScreen : ScreensNavigation(route = "detail_screen")
    object BookMarkScreen : ScreensNavigation(route = "book_mark_screen")





}