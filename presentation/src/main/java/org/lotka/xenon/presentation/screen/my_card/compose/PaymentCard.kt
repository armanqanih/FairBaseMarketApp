package org.lotka.xenon.presentation.screen.my_card.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card

import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

import androidx.compose.ui.graphics.painter.Painter

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.lotka.xenon.domain.util.Constants.SpaceMedium
import org.lotka.xenon.domain.util.Constants.SpaceSmall

@Composable
fun PaymentCard(
    modifier: Modifier = Modifier,
    image: Painter? = null,
    title: String = "",
    description: String = "",
    isCardSelected: Boolean = false, // Track if the card is selected
    onCardClick: () -> Unit, // Add an event handler for card clicks
) {
    val borderColor =
        if (isCardSelected) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface.copy(
            alpha = 0.5f
        )

    Card(
        onClick = {
            onCardClick()
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(SpaceSmall.dp)
            .clip(RoundedCornerShape(SpaceMedium))
            .height(100.dp)
            .border(
                width = 2.dp,
                color = borderColor, // Change border color when selected
                shape = RoundedCornerShape(SpaceMedium)
            )
        ,
             colors = CardDefaults.cardColors(
             containerColor = if (isCardSelected) MaterialTheme.colors.primary.copy(alpha = 0.1f)
             else MaterialTheme.colors.background
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SpaceMedium.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(SpaceSmall.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(SpaceSmall.dp)
            ) {
                image?.let {
                    Image(
                        painter = it,
                        contentDescription = title,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Text(
                    text = title,
                    style = MaterialTheme.typography.body1.copy(fontSize = 14.sp),
                    color = if (isCardSelected) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Text(
                text = description,
                style = MaterialTheme.typography.body2.copy(fontSize = 10.sp),
                color = if (isCardSelected) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
