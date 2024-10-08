package org.lotka.xenon.presentation.screen.explore.compose

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.delay
import org.lotka.xenon.presentation.R
import org.lotka.xenon.domain.util.Constants.SpaceMedium

@Composable
fun HeaderSection(
    images: List<Int> = listOf(R.drawable.banner1, R.drawable.banner2)
) {
    var currentIndex by remember { mutableIntStateOf(0) }

    if (images.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Oops Image Is Null !!",
                style = MaterialTheme.typography.h2,
                fontWeight = FontWeight.Bold
            )
        }
    } else {
        // Start the carousel only if the images list is not empty
        LaunchedEffect(Unit) {
            while (true) {
                delay(10000L)  // Change image every 3 seconds
                currentIndex = (currentIndex + 1) % images.size
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .clip(shape = RoundedCornerShape(SpaceMedium))
                    .shadow(elevation = 3.dp)
            ) {
                Crossfade(targetState = images[currentIndex], label = "") { image ->
                    Image(
                        painter = rememberAsyncImagePainter(model = image),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(SpaceMedium)),
                        contentDescription = "header image"
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
            ) {
                images.forEachIndexed { index, _ ->
                    val width by animateDpAsState(
                        targetValue = if (index == currentIndex) 20.dp else 8.dp, // Adjust width for current index
                        label = ""
                    )
                    val height by animateDpAsState(
                        targetValue = 8.dp, // Keep height constant or modify as needed
                        label = ""
                    )

                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .width(width)
                            .height(height)
                            .clip(CircleShape)
                            .background(
                                if (index == currentIndex)
                                    MaterialTheme.colors.primary
                                else
                                    MaterialTheme.colors.onBackground
                            )
                    )
                }
            }
        }
    }
}
