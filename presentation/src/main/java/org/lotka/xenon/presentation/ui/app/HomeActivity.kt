package org.lotka.xenon.presentation.ui.app

import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.lotka.xenon.presentation.compose.StandardScaffold
import org.lotka.xenon.presentation.screen.auth.login.GoogleAuthUiClient
import org.lotka.xenon.presentation.theme.CleanArchitectureMovieAppTheme
import org.lotka.xenon.presentation.ui.navigation.ScreensNavigation


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {



    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            val navController = rememberNavController()
            val keyboardController = LocalSoftwareKeyboardController.current

            CleanArchitectureMovieAppTheme {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    if (keyboardController != null) {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentRoute = navBackStackEntry?.destination?.route

                        StandardScaffold(
                            navController = navController,
                            showBottomBar = currentRoute in listOf(
                                ScreensNavigation.ProfileScreen.route,
                                ScreensNavigation.ExploreScreen.route
                            ),
                            modifier = Modifier.fillMaxSize(),

                            ) {
                        HomeApp(
                            activity = this@HomeActivity,
                            navController = navController,
                            isDarkTheme = false,
                            onToggleTheme = { },
                            keyboardController = keyboardController,
                            )
                    }}
                }





            }

        }





    }


}
