package org.lotka.xenon.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.ShoppingCart

import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.rounded.BorderColor
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.lotka.xenon.domain.util.Constants.SpaceMedium
import org.lotka.xenon.domain.util.Constants.SpaceSmall
import org.lotka.xenon.presentation.ui.navigation.ScreensNavigation


@Composable
fun StandardScaffold(
    navController: NavController,
    modifier: Modifier = Modifier,
    showBottomBar: Boolean = true,
    content: @Composable () -> Unit,
    )
{
    val items = listOf(
        BottomNavItem(
            title = "Explore",
            route =
            ScreensNavigation.ExploreScreen.route,
            selectedItem = Icons.Filled.Explore,
            unSelectedItem = Icons.Outlined.Explore,

        ),
        BottomNavItem(
            title = "Cart",
            route = ScreensNavigation.MyCardScreen.route,
            selectedItem = Icons.Filled.ShoppingCart,
            unSelectedItem = Icons.Outlined.ShoppingCart,
            badgeCount = 2
        ),

        BottomNavItem(
            title = "WishList",
            route = ScreensNavigation.WishList.route
            ,
            selectedItem = Icons.Rounded.Favorite,
            unSelectedItem = Icons.Rounded.FavoriteBorder,

            ),

        BottomNavItem(
            title = "MyOrder",
            route = ScreensNavigation.MyOrderScreen.route
            ,
            selectedItem = Icons.Rounded.BorderColor,
            unSelectedItem = Icons.Rounded.BorderColor,
            hasNews = false
        ),
        BottomNavItem(
            title = "Profile",
            route = ScreensNavigation.ProfileScreen.route
            ,
            selectedItem = Icons.Rounded.Person,
            unSelectedItem = Icons.Rounded.Person,
            hasNews = false
        ),
    )
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }




    Surface(modifier = modifier.fillMaxSize()) {

        Scaffold(
            bottomBar = {
                if(showBottomBar){
                    NavigationBar (
                        containerColor = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .padding(horizontal = SpaceSmall.dp, vertical =SpaceSmall.dp )
                            .clip(shape = RoundedCornerShape(SpaceMedium))
                            .height(75.dp)
                    ){

                        items.forEachIndexed { index, item ->
                            NavigationBarItem(
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor =  MaterialTheme.colors.primary,
                                ),
                                selected = selectedItemIndex == index,
                                onClick = {
                                    selectedItemIndex = index
                                    if(navController.currentDestination?.route != item.route){
                                        navController.navigate(item.route)
                                    }
                                },
                                label = {
                                    Text(text = item.title,
                                        color = MaterialTheme.colors.onBackground,
                                        style = MaterialTheme.typography.body2
                                    )
                                },
                                alwaysShowLabel = false,
                                icon = {
                                    BadgedBox(
                                        modifier = Modifier.background(
                                            color = MaterialTheme.colors.primary
                                        )
                                        ,badge = {
                                            if(item.badgeCount != null) {
                                                Badge {
                                                    androidx.compose.material3.Text(text = item.badgeCount.toString())
                                                }
                                            } else if(item.hasNews) {
                                                Badge()
                                            }
                                        }
                                    ) {
                                        Icon(
                                            modifier = Modifier.size(28.dp),
                                            imageVector = if (index == selectedItemIndex) {
                                                item.selectedItem
                                            } else item.unSelectedItem,
                                            contentDescription = item.title,
                                        )
                                    }
                                },

                                )
                        }
                    }
                }

            }
        ) {
            Column(modifier = Modifier.padding(it)) {
                content()
            }

        }

    }

}


data class BottomNavItem(
    val title: String,
    val route:String,
    val selectedItem: ImageVector,
    val unSelectedItem: ImageVector,
    val hasNews: Boolean = false,
    val badgeCount: Int? = null

)