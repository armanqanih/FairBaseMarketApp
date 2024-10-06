package org.lotka.xenon.presentation.screen.detail

import android.widget.Space
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import org.lotka.xenon.domain.model.Items
import org.lotka.xenon.domain.util.Constants.SpaceLarge
import org.lotka.xenon.domain.util.Constants.SpaceMedium
import org.lotka.xenon.domain.util.Constants.SpaceSmall
import org.lotka.xenon.presentation.R

@Composable
fun DetailItem(

) {



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
                            .data(
                                R.drawable.cat2_1
                            )
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
                items(5){
                    ImageOfToolsRow(image = "")
                }
            }
            Row (modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    modifier = Modifier.weight(8f),
                    text = "Business Laptop",
                    style = MaterialTheme.typography.h2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colors.onSurface
                    , fontWeight = FontWeight.SemiBold
                )
                Text(
                    modifier = Modifier.weight(2f),
                    text = "$500",
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

                        text = "4.7 Rating",
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
                items(3){
                    ToolsModel(modelText = "")
                }



            }


            Text(text = "How do you prioritize tasks when working on multiple features simultaneously? \n" +
                    "Possible Answer: \n" +
                    "Describe how you handle deadlines, prioritize features, and deal with potential blockers. It’s \n" +
                    "important to show that you can balance multiple responsibilities without compromising \n" +
                    "quality"   +"Describe how you handle deadlines, prioritize features, and deal with potential blockers. It’s \n" +
                    "important to show that you can balance multiple responsibilities without compromising \n" +
                    "quality",
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.surface,
                fontWeight = FontWeight.SemiBold

            )



        }


 }



