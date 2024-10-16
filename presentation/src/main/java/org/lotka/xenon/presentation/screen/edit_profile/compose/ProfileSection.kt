package org.lotka.xenon.presentation.screen.edit_profile.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import org.lotka.xenon.domain.util.Constants.SpaceSmall

@Composable
fun ProfileSection(
    profileImageUri:String?,
    onEditProfilePictureClick:()->Unit
){
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(140.dp)
        .clip(shape = CircleShape)
        ,
        contentAlignment = Alignment.Center
    ){

        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(profileImageUri)
                    .crossfade(true)
                    .error(android.R.drawable.ic_menu_report_image)
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .build()
            ),

            contentDescription = null,
            modifier = Modifier
                .padding(SpaceSmall.dp)
                .clip(shape = CircleShape)
                .size(120.dp)
        )
        IconButton(onClick = {
            onEditProfilePictureClick()
        },
            modifier = Modifier
                .offset(y = (+40).dp, x = (+40).dp)
                .clip(shape = CircleShape)
                .size(35.dp)
                .background(MaterialTheme.colors.primary.copy(alpha = 1f))

            ,
        ) {
            Icon(imageVector = Icons.Default.Edit,
                contentDescription ="Edit Profile"
                , tint = MaterialTheme.colors.background
            )
        }


    }

}