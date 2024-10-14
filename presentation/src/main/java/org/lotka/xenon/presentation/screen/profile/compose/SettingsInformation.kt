package org.lotka.xenon.presentation.screen.profile.compose

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import com.google.firebase.annotations.concurrent.Background
import org.lotka.xenon.domain.util.Constants.SpaceLarge
import org.lotka.xenon.domain.util.Constants.SpaceMedium
import org.lotka.xenon.domain.util.Constants.SpaceSmall
import org.lotka.xenon.presentation.R
import org.lotka.xenon.presentation.ui.navigation.ScreensNavigation

@Composable
fun SettingsInformation (
   showHeaderName:Boolean = false,
   headerName:String = "",
   icon: ImageVector ,
   iconBackground: Color = MaterialTheme.colors.surface,
   title:String = "",
   onNavigateTo:( )->Unit= {}
){
   Column(modifier = Modifier.fillMaxWidth()) {
       if (showHeaderName){
           Text(
               text = headerName,
               style = MaterialTheme.typography.body1.copy(
                   fontSize = 18.sp
               ),
               maxLines = 1,
               overflow = TextOverflow.Ellipsis,
               color = MaterialTheme.colors.onSurface,
               fontWeight = FontWeight.Bold
           )
       } else null
       Spacer(modifier = Modifier.height(SpaceLarge.dp))

       Row (modifier = Modifier
           .fillMaxWidth().padding(start = 8.dp)
           .clickable { onNavigateTo() },
           horizontalArrangement = Arrangement.SpaceBetween,
           verticalAlignment = Alignment.CenterVertically
       ){
           Row (modifier = Modifier.weight(8f),
               horizontalArrangement = Arrangement.spacedBy(SpaceLarge.dp),
               verticalAlignment = Alignment.CenterVertically
           ){
               IconButton(
                   onClick = {}
                   ,
                   modifier = Modifier
                       .size(25.dp)
                       .clip(CircleShape)
                       .clickable {

                       }
                       .background(iconBackground)
               ) {
                   Icon(imageVector = icon
                       , contentDescription = "iconSetting",
                       tint = MaterialTheme.colors.background,
                               modifier = Modifier
                               .size(25.dp)

                   )

               }

                   Text(
                       text = title,
                       style = MaterialTheme.typography.body1,
                       maxLines = 1,
                       overflow = TextOverflow.Ellipsis,
                       fontWeight = FontWeight.SemiBold,
                       color = MaterialTheme.colors.onSurface
                   )



           }
           Icon(imageVector = Icons.Default.ArrowForwardIos,
               contentDescription = "Forward",
               tint = MaterialTheme.colors.onSurface,

           )

       }
   }


}