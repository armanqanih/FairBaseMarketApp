package org.lotka.xenon.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonColors
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.lotka.xenon.domain.util.Constants.SpaceMedium
import org.lotka.xenon.domain.util.Constants.SpaceSmall

@Composable
fun StandardButton(
    modifier: Modifier=Modifier,
    title:String="",
    onButtonClick:()->Unit={}
){

    TextButton(onClick = { onButtonClick() }
        ,
        modifier = modifier.fillMaxWidth()
            .clip(shape = RoundedCornerShape(SpaceMedium))
            .background(color = MaterialTheme.colors.primary)

        ) {
       Text(text = title,
           style = MaterialTheme.typography.body1,
           color = MaterialTheme.colors.background
           , fontWeight = FontWeight.Bold
           )
    }


}