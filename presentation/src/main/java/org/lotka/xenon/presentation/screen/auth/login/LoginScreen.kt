package org.lotka.xenon.presentation.screen.auth.login

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity.RESULT_OK
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.coroutines.launch
import org.lotka.xenon.domain.util.Constants.SpaceLarge
import org.lotka.xenon.domain.util.Constants.SpaceMedium
import org.lotka.xenon.domain.util.Constants.SpaceSmall
import org.lotka.xenon.domain.util.Constants.SpaceToLarge
import org.lotka.xenon.domain.util.Constants.SpaceVeryLarge
import org.lotka.xenon.domain.util.error.AuthError
import org.lotka.xenon.presentation.R
import org.lotka.xenon.presentation.compose.StandardTextField
import org.lotka.xenon.presentation.ui.navigation.ScreensNavigation
import org.lotka.xenon.presentation.util.UiEvent



@Composable
fun LoginScreen(

    onNavigateTo: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    val passwordState = viewModel.passwordState.collectAsState().value
    val emailState = viewModel.emailState.collectAsState().value

    val state = viewModel.state.collectAsState().value
    val scaffoldState = rememberScaffoldState()

    val sharedPreferences = LocalContext.current.getSharedPreferences(
        "your_app_preferences", Context.MODE_PRIVATE)
    val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

    val scope = rememberCoroutineScope()

     val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = context,
            oneTapClient = Identity.getSignInClient(context)
        )
    }


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if(result.resultCode == RESULT_OK) {
                scope.launch {
                    val signInResult = googleAuthUiClient.signInWithIntent(
                        intent = result.data ?: return@launch
                    )
                    viewModel.onSignInResult(signInResult)
                }
            }
        }
    )


    LaunchedEffect(key1 = true) {
        if (viewModel.isUserLoggedIn()) {
            onNavigateTo(ScreensNavigation.ExploreScreen.route)
        } else {
            viewModel.eventFlow.collect { event ->
                when (event) {
                    is UiEvent.ShowSnakeBar -> {
                        scaffoldState.snackbarHostState.showSnackbar(message = event.message)
                    }
                    is UiEvent.Navigate -> {
                        viewModel.saveLoginStatus(true)
                        onNavigateTo(ScreensNavigation.ExploreScreen.route)
                    }

                    UiEvent.onNavigateUp -> onNavigateUp()
                }
            }
        }
    }

    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            scaffoldState.snackbarHostState.showSnackbar(message = error)
        }
    }

    LaunchedEffect(key1 = state.isSignInSuccessful) {
        if(state.isSignInSuccessful) {
            onNavigateTo(ScreensNavigation.ProfileScreen.route)
        }
    }


    androidx.compose.material.Scaffold(
        modifier = Modifier.fillMaxSize()
        ,scaffoldState = scaffoldState
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(
                    start = SpaceLarge.dp,
                    end = SpaceLarge.dp,
                    top = SpaceLarge.dp,
                    bottom = 50.dp
                )
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            ) {
                Text(
                    text = stringResource(R.string.login),
                    style = MaterialTheme.typography.body1,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(SpaceMedium.dp))
                StandardTextField(
                    modifier = Modifier.clip(
                        shape = RoundedCornerShape(SpaceSmall.dp)),
                    value = emailState.text,
                    hint = stringResource(R.string.enter_user_email),
                    onValueChange = {
                        viewModel.onEvent(LoginEvent.EnterEmail(it))
                    },
                    singleLine = true,
                    keyboardType = KeyboardType.Email,
                    error = when(emailState.error){
                        is AuthError.FieldEmpty -> {
                            stringResource(R.string.this_field_cant_be_empty)
                        }

                        null -> ""
                        else -> {""}
                    }



                )
                Spacer(modifier = Modifier.height(SpaceMedium.dp))
                StandardTextField(
                    modifier = Modifier.clip(
                        shape = RoundedCornerShape(SpaceSmall.dp)),
                    value = passwordState.text,
                    hint = stringResource(R.string.Password),
                    onValueChange = {
                        viewModel.onEvent(LoginEvent.EnterPassword(it))
                    },
                    singleLine = true,
                    keyboardType = KeyboardType.Password,
                    error = when(passwordState.error){
                        is AuthError.FieldEmpty -> {
                            stringResource(R.string.this_field_cant_be_empty)
                        }
                        else -> ""
                    },
                    isPasswordVisible = passwordState.isPasswordVisible,
                    onPasswordToggleClick = {
                        viewModel.onEvent(LoginEvent.IsPasswordVisibility)
                    }
                )

                Spacer(modifier = Modifier.height(SpaceMedium.dp))
                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary
                    ),
                    onClick = {
                        viewModel.onEvent(LoginEvent.Login)
                              },
                    modifier = Modifier
                        .height(50.dp)
                        .width(120.dp)
                        .align(Alignment.End)
                        .clip(shape = RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colors.primary),
                    enabled =  emailState.text.isNotEmpty() &&
                               passwordState.text.isNotEmpty()

                ) {
                    val textColor = if (emailState.text.isNotEmpty() && passwordState.text.isNotEmpty()) {
                        Color.White
                    } else {
                        Color.White
                    }

                    Text(
                        text = stringResource(R.string.Login),
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold,
                        color = textColor

                    )
                }

                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(SpaceMedium.dp)
                    )
                }
                Spacer(modifier = Modifier.height(SpaceLarge.dp))

                val annotatedText = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.Black,

                            )
                    ) {
                        append("Don't you have an account? ")
                    }
                    pushStringAnnotation(tag = "REGISTER", annotation = "register")
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.primary, // optional, for underlining the text
                        )
                    ) {
                        append("Register")
                    }
                    pop()
                }

                ClickableText(
                    text = annotatedText,
                    onClick = { offset ->
                        annotatedText.getStringAnnotations(tag = "REGISTER", start = offset, end = offset)
                            .firstOrNull()?.let {
                                onNavigateTo(ScreensNavigation.RegisterScreen.route)
                            }
                    },
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(start = SpaceToLarge.dp,
                        top = SpaceVeryLarge.dp)
                )


            }



//            Google SignIn Ui

            Button(
                onClick = {
                    scope.launch {
                        val signInIntentSender = googleAuthUiClient.signIn()
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                signInIntentSender ?: return@launch
                            ).build()
                        )
                    }
//                 viewModel.googleSignIn(context)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .align(Alignment.BottomCenter)
                    .padding(5.dp),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(0.dp),
            ) {
                Row(
                    modifier = Modifier
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    MaterialTheme.colors.primary,
                                    MaterialTheme.colors.secondary
                                ),
                            )
                        )
                        .fillMaxSize()
                        .padding(10.dp),
                    // contentAlignment = Alignment.Center
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.google),
                        contentDescription = "Google"
                    )
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        text = "Continue with Google",
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }



        }
    }
