package org.lotka.xenon.presentation.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun StandardHeaderText(
    modifier: Modifier=Modifier,
    name: String = "",
    onSeeAllTextClick: () -> Unit = {}
){
    Row (modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
        ){
        Text(text = name,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onSurface,
            fontWeight = FontWeight.Bold
        )
        TextButton(onClick = {
            onSeeAllTextClick()
        }
        ) {
            Text(text = "See All",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Bold
            )
        }


    }
}