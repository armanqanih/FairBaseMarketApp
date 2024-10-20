package org.lotka.xenon.presentation.screen.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocationAlt
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCartCheckout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.lotka.xenon.domain.util.Constants
import org.lotka.xenon.domain.util.Constants.SpaceMedium
import org.lotka.xenon.domain.util.Constants.SpaceMidumLarge
 
import org.lotka.xenon.presentation.screen.profile.compose.HeaderSection
import org.lotka.xenon.presentation.screen.profile.compose.SettingsInformation
import org.lotka.xenon.presentation.theme.Blue
import org.lotka.xenon.presentation.theme.Gray
import org.lotka.xenon.presentation.theme.GreenLand
import org.lotka.xenon.presentation.theme.OrangeColor
import org.lotka.xenon.presentation.theme.Yellow
import org.lotka.xenon.presentation.ui.navigation.ScreensNavigation


@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel= hiltViewModel(),
    navigateToEditProfile:(String)->Unit = {}
) {

   val state =  profileViewModel.state.collectAsState().value
    val user = state.user

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
        ){
        if (state.isLoading){
            CircularProgressIndicator()
        }
        state?.error?.let {
            Text(text = "Oops Somethings ${state.error}",
                style = MaterialTheme.typography.h2,
                color = Color.Red,
                fontWeight = FontWeight.Bold
                )
        }
    }


    Column(modifier = Modifier
        .fillMaxSize()
        .padding(start = SpaceMedium.dp, end = SpaceMedium.dp)
        ,

        ) {
        Spacer(modifier = Modifier.height(Constants.SpaceLarge.dp))
        HeaderSection(
            userProfilePicture = user?.profileImageUrl?:"",
            userName = user?.username?:"Arman sherwanii",
            onHeaderClick = {
            navigateToEditProfile(
                ScreensNavigation.EditProfileScreen.route
            )
        })
        Spacer(modifier = Modifier.height(Constants.SpaceLarge.dp))
        SettingsInformation(
            iconBackground = Blue,
            showHeaderName = true,
            headerName = "Order",
            icon = Icons.Default.ShoppingCartCheckout,
            title = "All Order",
            onNavigateTo = {

            }

        )
        Spacer(modifier = Modifier.height(SpaceMedium.dp))
        SettingsInformation(
            iconBackground =  Gray,
            headerName = "",
            icon = Icons.Default.Search,
            title = "Track Order",
            onNavigateTo = {

            }

        )
        Spacer(modifier = Modifier.height(SpaceMedium.dp))
        SettingsInformation(
            iconBackground = Yellow,
            headerName = "",
            icon = Icons.Default.AddLocationAlt,
            title = "Billing And Address",
            onNavigateTo = {

            }

        )
        Spacer(modifier = Modifier.height(SpaceMidumLarge.dp))
        SettingsInformation(
            iconBackground = GreenLand,
            showHeaderName = true,
            headerName = "Notifications",
            icon = Icons.Default.NotificationsActive,
            title = "Notifications",
            onNavigateTo = {

            }

        )
        Spacer(modifier = Modifier.height(SpaceMidumLarge.dp))
        SettingsInformation(
            iconBackground = MaterialTheme.colors.primary,
            showHeaderName = true,
            headerName = "Regional",
            icon = Icons.Default.Language,
            title = "Language",
            onNavigateTo = {

            }

        )
        Spacer(modifier = Modifier.height(SpaceMedium.dp))
        SettingsInformation(
            iconBackground = OrangeColor,
            icon = Icons.Default.Logout,
            title = "LogOut",
            onNavigateTo = {
             profileViewModel.logoutUser()
            }

        )
    }


}
