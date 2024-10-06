package org.lotka.xenon.presentation.screen.detail.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.lotka.xenon.domain.util.Constants.SpaceLarge

@Composable
fun ToolsModel(
  toolsModel:String="",
  isSelected: Boolean=false,   // New parameter to check if this model is selected
  onModelSelected: () -> Unit ={}
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground, // Highlight if selected
                shape = RoundedCornerShape(SpaceLarge)
            )
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        TextButton(
            onClick = onModelSelected, // Handle click event
            modifier = Modifier
                .clip(RoundedCornerShape(SpaceLarge))
                .background(MaterialTheme.colors.onBackground)
        ) {
            Text(
                text = toolsModel,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface
            )
        }
    }
}