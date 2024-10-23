package org.lotka.xenon.presentation.screen.edit_profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import org.lotka.xenon.domain.util.Constants.SpaceMedium
import org.lotka.xenon.domain.util.Constants.SpaceSmall
import org.lotka.xenon.domain.util.Constants.SpaceToLarge
import org.lotka.xenon.presentation.compose.StandardButton
import org.lotka.xenon.presentation.compose.StandardTextField
import org.lotka.xenon.presentation.compose.StandardTopBar
import org.lotka.xenon.presentation.screen.auth.login.LoginViewModel
import org.lotka.xenon.presentation.screen.edit_profile.compose.ProfileSection
import org.lotka.xenon.presentation.util.CropActivityResultContract
import org.lotka.xenon.presentation.util.PasswordTextFieldState
import org.lotka.xenon.presentation.util.StandardTextFieldState
import org.lotka.xenon.presentation.util.UiEvent

@Composable
fun EditProfileScreen(
    viewModel: EditProfileViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit,
) {


    val state = viewModel.state.collectAsState().value
    val user = viewModel.state.collectAsState().value.user
    val passwordState = viewModel.passwordState.collectAsState().value


    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is UiEvent.ShowSnakeBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                    )
                }

                is UiEvent.onNavigateUp -> {
                    onNavigateUp()
                }

                is UiEvent.Navigate -> TODO()
            }
        }
    }

    val cropProfileImageLauncher = rememberLauncherForActivityResult(
        contract = CropActivityResultContract(1f,1f)
    ) { uri ->
        uri?.let {
            viewModel.onEvent(EditProfileEvent.CropProfilePicture(it))
        }
    }


    val profilePictureGalleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            cropProfileImageLauncher.launch(it)
        }
    }


    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        if (state.isLoading){
            CircularProgressIndicator()
        }
        state.error.let {
            Text(text = "Oops Somethings Went wrong ${state.error.toString()}",
                style = MaterialTheme.typography.h2,
                color = Color.Red,
                fontWeight = FontWeight.Bold
            )
        }
    }



Scaffold (scaffoldState = scaffoldState){
    Column(
        modifier = Modifier
            .fillMaxSize().padding(it)
            .padding(start = SpaceSmall.dp, end = SpaceSmall.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StandardTopBar(
            onNavigateUp = onNavigateUp,
            showCancelIcon = true,
            title = {
                Text(
                    text = "Edit Profile",
                    style = MaterialTheme.typography.body1.copy(
                        fontSize = 24.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colors.onSurface,
                    fontWeight = FontWeight.SemiBold
                )
            }
        )
        Spacer(modifier = Modifier.height(SpaceToLarge.dp))
        ProfileSection(
            profileImageUri = state.profileImageUri?.toString(),
            onEditProfilePictureClick = {
                profilePictureGalleryLauncher.launch("image/*")
            }
        )
        state.userNameState?.let { it1 ->
            StandardTextField(
                modifier = Modifier.padding(start = SpaceSmall.dp, end = SpaceSmall.dp),
                value = it1,
                hint = "User Name",
                maxLines = 1,
                keyboardType = KeyboardType.Text,
                onValueChange = {
                    viewModel.onEvent(
                        EditProfileEvent.UserNameChange(it)
                    )
                }

            )
        }

        Spacer(modifier = Modifier.height(SpaceMedium.dp))
        state.emailState?.let { it1 ->
            StandardTextField(
                modifier = Modifier.padding(start = SpaceSmall.dp, end = SpaceSmall.dp),
                value = it1,
                hint = "Email",
                maxLines = 1,
                onValueChange = {
                    viewModel.onEvent(
                        EditProfileEvent.EmailChange(it)
                    )
                },
                keyboardType = KeyboardType.Email

            )
        }
        Spacer(modifier = Modifier.height(SpaceMedium.dp))
//        StandardTextField(
//            modifier = Modifier.padding(start = SpaceSmall.dp, end = SpaceSmall.dp),
//            value = passwordState.text,
//
//            hint = "password",
//            maxLines = 1,
//            onValueChange = {
//                viewModel.onEvent(
//                    EditProfileEvent.PasswordChange(
//                        PasswordTextFieldState(text = it)
//                    )
//                )
//            },
//            keyboardType = KeyboardType.Password,
//            isPasswordVisible = passwordState.isPasswordVisible,
//            onPasswordToggleClick = {
//                viewModel.onEvent(EditProfileEvent.IsPasswordVisibility)
//            },
//
//
//            )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = SpaceSmall.dp),
            text = "forget and Change password",
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.surface,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.SemiBold

        )
        Spacer(modifier = Modifier.height(80.dp))
        StandardButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = SpaceSmall.dp, end = SpaceSmall.dp)
                .size(60.dp),
            title = "Save",
            onButtonClick = {
                viewModel.onEvent(EditProfileEvent.UpdateProfile)
            }
        )


    }

}

}