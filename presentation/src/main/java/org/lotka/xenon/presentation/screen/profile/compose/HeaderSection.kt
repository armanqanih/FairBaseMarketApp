package org.lotka.xenon.presentation.screen.profile.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import org.lotka.xenon.domain.util.Constants.SpaceLarge
import org.lotka.xenon.domain.util.Constants.SpaceMedium
import org.lotka.xenon.domain.util.Constants.SpaceSmall
import org.lotka.xenon.presentation.R
import org.lotka.xenon.presentation.ui.navigation.ScreensNavigation

@Composable
fun HeaderSection (
   onHeaderClick: ()-> Unit ={}
){

    Row (modifier = Modifier.fillMaxWidth()
        .clickable { onHeaderClick() }
        ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
        ){
        Row (modifier = Modifier.weight(7f),
            horizontalArrangement = Arrangement.spacedBy(SpaceMedium.dp),
            verticalAlignment = Alignment.CenterVertically
            ){
            Card(
                backgroundColor = MaterialTheme.colors.background ,
                modifier = Modifier
                    .clip(CircleShape)
                    .border(width = 3.dp, shape = CircleShape,
                        color =  MaterialTheme.colors.primary
                        )
                    .size(65.dp)
                    .clickable {
                    }

            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(R.drawable.pobg)
                            .crossfade(true)
                            .error(android.R.drawable.ic_menu_report_image)
                            .placeholder(android.R.drawable.ic_menu_gallery)
                            .size(Size.ORIGINAL)
                            .scale(Scale.FILL)
                            .build()
                    ),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(6.dp)
                        .clip(CircleShape)
                )

            }
            Column (modifier = Modifier.fillMaxWidth(),

                ){
                Text(
                    text = "Arman Sherwanii",
                    style = MaterialTheme.typography.body1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onSurface
                )
                Text(
                    text = "Edit personal information",
                    style = MaterialTheme.typography.body2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colors.onSurface
                )
            }


        }
        Icon(imageVector = Icons.Default.ArrowForwardIos,
            contentDescription = "Forward",
            tint = MaterialTheme.colors.onSurface,
            )

    }

}