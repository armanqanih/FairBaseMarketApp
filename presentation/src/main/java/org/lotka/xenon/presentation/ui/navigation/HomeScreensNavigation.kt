package org.lotka.xenon.presentation.ui.navigation

sealed class ScreensNavigation(val route: String) {




    object HomeScreen : ScreensNavigation(route = "home_screen")
    object DetailScreen : ScreensNavigation(route = "detail_screen")
    object BookMarkScreen : ScreensNavigation(route = "book_mark_screen")





}