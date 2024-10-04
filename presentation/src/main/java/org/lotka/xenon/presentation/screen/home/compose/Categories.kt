package org.lotka.xenon.presentation.screen.home.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import org.lotka.xenon.domain.model.Category
import org.lotka.xenon.domain.util.Constants.SpaceLarge
import org.lotka.xenon.domain.util.Constants.SpaceMedium
import org.lotka.xenon.domain.util.Constants.SpaceSmall
import org.lotka.xenon.presentation.compose.StandardHeaderText
import org.lotka.xenon.presentation.screen.home.HomeViewModel


@Composable
fun Categories(
    categories: List<Category>
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        StandardHeaderText(name = "Categories", onSeeAllTextClick = {})
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(categories) { category ->
                CategoryItem(
                    category = category,
                    onItemClick = {}
                    )
            }
        }
    }
}


@Composable
fun CategoryItem(
    category: Category,
    onItemClick: (String) -> Unit
) {
    val isClicked = remember { mutableStateOf(false) }

    // Animate the background color change
    val backgroundColor by animateColorAsState(
        targetValue = if (isClicked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground,
        animationSpec = tween(durationMillis = 500),
        label = "" // Smooth transition over 500 milliseconds
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(SpaceLarge)
            )
            .clickable {
                isClicked.value = !isClicked.value
                category.title?.let { onItemClick(it) }
            }
            .padding(SpaceMedium.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                isClicked.value = !isClicked.value
                category.title?.let { onItemClick(it) }
            },
            modifier = Modifier
                .clip(RoundedCornerShape(SpaceLarge))
                .size(28.dp)
                .background(MaterialTheme.colors.onBackground)
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(category.picUrl)
                        .crossfade(true)
                        .error(android.R.drawable.ic_menu_report_image)
                        .placeholder(android.R.drawable.ic_menu_gallery)
                        .size(Size.ORIGINAL)
                        .scale(Scale.FILL)
                        .build()
                ),
                contentDescription = null,
                modifier = Modifier
                    .background(color = backgroundColor)
                    .clip(RoundedCornerShape(SpaceLarge.dp))
                    .size(64.dp)
            )
        }

        // Animated visibility for the title text
        AnimatedVisibility(
            visible = isClicked.value,
            enter = fadeIn(animationSpec = tween(300)) + expandHorizontally(),
            exit = fadeOut(animationSpec = tween(300)) + shrinkHorizontally()
        ) {
            category.title?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = SpaceSmall.dp)
                )
            }
        }
    }
}
