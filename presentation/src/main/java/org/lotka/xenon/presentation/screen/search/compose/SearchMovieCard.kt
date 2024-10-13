package org.lotka.xenon.presentation.screen.search.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import org.lotka.xenon.domain.util.Constants.SpaceLarge
import org.lotka.xenon.domain.util.Constants.SpaceSmall
import org.lotka.xenon.domain.util.Constants.SpaceToLarge
import org.lotka.xenon.presentation.R

@Composable
fun SearchItemCard(
    onNavigateTo:()-> Unit = {},
    toolImage:String? = "",
    nameOfTool:String="",
    toolPrice:String? ="",
    rating:String?=""
) {

    Row(
        modifier = Modifier.fillMaxWidth()
            .clickable {
                onNavigateTo()
            }
        ,
    ) {
        Row(
            modifier = Modifier.weight(6f),
            horizontalArrangement = Arrangement.spacedBy(SpaceSmall.dp)
        ) {
            Card(
                modifier = Modifier
                    .clip(RoundedCornerShape(SpaceSmall))
                    .height(80.dp)
                    .width(80.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colors.onBackground
                )
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(toolImage) // Image resource
                            .crossfade(true)
                            .error(android.R.drawable.ic_menu_report_image)
                            .placeholder(android.R.drawable.ic_menu_gallery)
                            .size(Size.ORIGINAL)
                            .scale(Scale.FILL)
                            .build()
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(SpaceSmall.dp)
                        .clip(RoundedCornerShape(SpaceLarge.dp))
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(SpaceLarge.dp)
            ) {
                Column {
                    Text(
                        text = nameOfTool, // Replace with dynamic data
                        style = MaterialTheme.typography.body1,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colors.onSurface,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "$${toolPrice}",
                        style = MaterialTheme.typography.body1,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                    Row {
                        Icon(imageVector = Icons.Default.Star,
                            contentDescription = "Star" ,
                            tint = MaterialTheme.colors.secondary
                        )

                        Text(
                            text = "${rating}",
                            style = MaterialTheme.typography.body1,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colors.onSurface,
                            fontWeight = FontWeight.SemiBold
                        )
                    }


                }


            }
        }


    }
}
