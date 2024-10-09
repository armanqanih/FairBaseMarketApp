package org.lotka.xenon.presentation.screen.my_card.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
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
fun MyOrderCard(
    nameOfTool:String="",
    toolPrice:String="",
    toolTotalPrice:String="",
    onPlusButtonClick:()-> Unit = {},
    onMinusButtonClick:()-> Unit = {},
    quantityText:String=""
) {



    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.weight(6f),
            horizontalArrangement = Arrangement.spacedBy(SpaceSmall.dp)
        ) {
            Card(
                modifier = Modifier
                    .clip(RoundedCornerShape(SpaceSmall))
                    .height(100.dp)
                    .width(100.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colors.onBackground
                )
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(R.drawable.cat2_1) // Image resource
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
                        text = "${toolPrice}",
                        style = MaterialTheme.typography.body1,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                // Total price text that updates with quantity
                Text(
                    text = "$${toolTotalPrice}",
                    style = MaterialTheme.typography.h2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colors.onSurface,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // Quantity controls (minus and plus buttons)
        Row(
            modifier = Modifier
                .weight(2f)
                .padding(top = 58.dp)
                .clip(RoundedCornerShape(SpaceSmall.dp))
                .background(MaterialTheme.colors.onBackground),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Minus button
            TextButton(onClick = {
              onMinusButtonClick()
            },
                modifier = Modifier
                    .padding(start = 2.dp, top = 2.dp, bottom = 2.dp)
                    .clip(RoundedCornerShape(SpaceToLarge))
                    .weight(0.5f)
                    .height(SpaceToLarge.dp)
                    .background(MaterialTheme.colors.background)
            ) {
                Text(
                    text = "-",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.onSurface
                )
            }

            // Display quantity
            Text(
                text = "$quantityText",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(start = 12.dp, end = 12.dp)
            )

            // Plus button
            TextButton(onClick = {
               onPlusButtonClick()
            },
                modifier = Modifier
                    .padding(start = 2.dp, top = 2.dp, bottom = 2.dp)
                    .clip(RoundedCornerShape(SpaceToLarge))
                    .weight(0.5f)
                    .height(SpaceToLarge.dp)
                    .background(MaterialTheme.colors.primary)
            ) {
                Text(
                    text = "+",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.onBackground
                )
            }
        }
    }
}
