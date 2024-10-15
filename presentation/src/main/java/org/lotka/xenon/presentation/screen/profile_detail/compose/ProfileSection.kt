package org.lotka.xenon.presentation.screen.profile_detail.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.lotka.xenon.presentation.R

@Composable
fun ProfileSection(
   onEditProfilePictureClick:()->Unit
){
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(170.dp)
        .clip(shape = CircleShape),
    ){

        Image(
            painter = painterResource(id = R.drawable.pobg),
            contentDescription = "",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(110.dp)
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.onSurface,
                    shape = CircleShape
                )
                .clickable { }

            ,
            contentScale = ContentScale.Crop

        )
        IconButton(onClick = {
            onEditProfilePictureClick()
        },
            modifier = Modifier
                .offset(y = (+80).dp, x = (+210).dp)
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