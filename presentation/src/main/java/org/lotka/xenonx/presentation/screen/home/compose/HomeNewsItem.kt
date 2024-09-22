package org.lotka.xenonx.presentation.screen.home.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import org.lotka.xenonx.presentation.R
import org.lotka.xenonx.presentation.util.dimens.SpaceLarge
import org.lotka.xenonx.presentation.util.dimens.SpaceMedium
import org.lotka.xenonx.presentation.util.dimens.SpaceSmall
import org.lotka.xenonx.presentation.util.dimens.SpaceToLarge


@Composable
fun HomeNewsItem(
    onNavigateToDetail:(String)->  Unit = {},
    modifier: Modifier = Modifier,
) {




    val imagePainter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(R.drawable.assasin)
            .size(Size.ORIGINAL)
            .build()
    )
    val imageState = imagePainter.state

    val defaultDominantColor = androidx.compose.material3.MaterialTheme.colorScheme.primaryContainer
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(shape = RoundedCornerShape(SpaceMedium))
            .shadow(elevation = 4.dp)
            .clickable {

            }
            .background(color = MaterialTheme.colors.surface),
    ) {

        if (imageState is AsyncImagePainter.State.Success) {
            val imageBitmap = imageState.result.drawable.toBitmap()
            Image(
                bitmap = imageBitmap.asImageBitmap(),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(
                        topStart = SpaceMedium,
                        topEnd = SpaceSmall)),
                contentDescription = "header image"

            )
        }
        if (imageState is AsyncImagePainter.State.Error) {
            Icon(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(20.dp))
                    .background(androidx.compose.material3.MaterialTheme.colorScheme.background)
                    .padding(32.dp)
                    .alpha(0.8f),
                painter = painterResource(id = R.drawable.ic_broken_image),
                contentDescription = "",
                tint = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
            )
        }

        if (imageState is AsyncImagePainter.State.Loading) {
            CircularProgressIndicator(
                color = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.Center)
                    .scale(0.5f)
            )
        }


        Column (modifier = Modifier
            .fillMaxWidth().background(
                Brush.verticalGradient(
                    colors = listOf(
                       MaterialTheme.colors.background,
                        dominantColor
                    )
                )
            )
            .padding(bottom = SpaceSmall)
            .align(Alignment.BottomCenter),

            ){
                Text(
                    modifier = Modifier
                        .fillMaxWidth().
                    padding(top = SpaceSmall, start = SpaceSmall),
                    text = "Title aaaaaaa",
                    maxLines = 1,
                    fontFamily = FontFamily.Serif,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colors.onBackground,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.body2,


                    )
            Text(
                    modifier = Modifier
                        .fillMaxWidth().
                    padding( top = 4.dp,start = SpaceSmall),
                    text = "12:00pm",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colors.onBackground,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.body2
                        .copy(fontSize = 10.sp)
                ,
                    )

            Text(
                modifier = Modifier
                    .fillMaxWidth().
                    padding( top = 4.dp,start = SpaceSmall),
                text = "thats a best news hello gys how are you very things well" ,
                maxLines = 2,
                fontFamily = FontFamily.Serif,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colors.onBackground,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.body2.copy(
                    fontSize = 10.sp
                ),
            )

            }



            }

        }





