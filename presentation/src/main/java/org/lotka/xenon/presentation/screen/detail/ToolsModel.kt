package org.lotka.xenon.presentation.screen.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import org.lotka.xenon.domain.util.Constants.SpaceLarge
import org.lotka.xenon.domain.util.Constants.SpaceSmall
import org.lotka.xenon.presentation.R

@Composable
fun ToolsModel(
  modelText:String=""
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colors.onBackground,
                shape = RoundedCornerShape(SpaceLarge)
            )
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
        , horizontalArrangement = Arrangement.Center
    ) {
        TextButton(
            onClick = {

            },
            modifier = Modifier
                .clip(RoundedCornerShape(SpaceLarge))
                .background(MaterialTheme.colors.onBackground)
        ) {
            Text(text = "Core i3",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface
                )
        }
    }
}