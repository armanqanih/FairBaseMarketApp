package org.lotka.xenon.presentation.screen.detail.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import org.lotka.xenon.domain.model.Item
import org.lotka.xenon.domain.util.Constants.SpaceLarge
import org.lotka.xenon.domain.util.Constants.SpaceMedium
import org.lotka.xenon.domain.util.Constants.SpaceSmall

@Composable
fun DetailItem(
  item:Item
) {

    var selectedImage by remember { mutableStateOf(item.picUrl?.firstOrNull()) }
    var selectedModel by remember { mutableStateOf(item.model?.firstOrNull()) }

        Column (modifier = Modifier.fillMaxWidth()
            , verticalArrangement = Arrangement.spacedBy(SpaceLarge.dp)
        ) {

            Card(
            backgroundColor = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .clip(RoundedCornerShape(SpaceSmall))

            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(selectedImage)
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
                        .padding(SpaceLarge.dp)
                        .clip(RoundedCornerShape(SpaceLarge.dp))
                )

            }


            LazyRow (modifier = Modifier.fillMaxWidth()
                , horizontalArrangement = Arrangement.spacedBy(SpaceSmall.dp)
            ){
                items(item.picUrl ?: emptyList()) { imageUrl ->
                    ImageOfToolsRow(
                        image = imageUrl,
                        onImageSelected = { selectedImage = imageUrl } // Update selected image when clicked
                    )
                }
            }

            Row (modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                item?.title?.let {
                    Text(
                        modifier = Modifier.weight(8f),
                        text = it,
                        style = MaterialTheme.typography.h2,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colors.onSurface, fontWeight = FontWeight.SemiBold
                    )
                }
                Text(
                    modifier = Modifier.weight(2f),
                    text = item.price.toString(),
                    style = MaterialTheme.typography.h2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colors.onSurface
                    , fontWeight = FontWeight.SemiBold
                )
            }
            Row (modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    modifier = Modifier.weight(6f),
                    text = "Select Model",
                    style = MaterialTheme.typography.body1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colors.onSurface
                    , fontWeight = FontWeight.SemiBold
                )

                Row ( modifier = Modifier.weight(3f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(SpaceSmall.dp)
                ){

                    Icon(imageVector =Icons.Default.Star,
                        contentDescription ="Star",
                        tint = MaterialTheme.colors.secondary
                    )

                    Text(

                        text = "${item.rating} Rating",
                        style = MaterialTheme.typography.body1,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colors.onSurface
                        , fontWeight = FontWeight.SemiBold
                    )


                }

            }


            LazyRow (modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(SpaceMedium.dp)
            ){
                items(item.model ?: emptyList()) { toolsModel ->
                    ToolsModel(
                        toolsModel = toolsModel,
                        isSelected = toolsModel == selectedModel,  // Check if the model is selected
                        onModelSelected = {
                            selectedModel = toolsModel // Update selected model on click
                        }
                    )
                }
            }


            item.description?.let {
                Text(text = it,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.surface,
                    fontWeight = FontWeight.SemiBold

                )
            }



        }


 }



