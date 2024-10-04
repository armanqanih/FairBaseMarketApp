package org.lotka.xenon.presentation.screen.home.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import org.lotka.xenon.presentation.compose.StandardHeaderText
import org.lotka.xenon.presentation.screen.home.HomeViewModel


@Composable
fun Categories(
    categories: List<Category>
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        StandardHeaderText("Categories")
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

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (isClicked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground,
                shape = RoundedCornerShape(SpaceLarge)
            )
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
                .size(40.dp)
                .background(MaterialTheme.colors.onBackground)
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(category.picUrl)
                        .crossfade(true)
                        .error(android.R.drawable.ic_menu_report_image) // Add fallback image on error
                        .placeholder(android.R.drawable.ic_menu_gallery) // Placeholder while loading
                        .size(Size.ORIGINAL) // Adjust size if needed
                        .scale(Scale.FILL)  // Optional scaling behavior
                        .build()
                ),
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(SpaceLarge))
                    .size(64.dp) // Set a size for the image
            )
        }

        if (isClicked.value) {
            category.title?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = SpaceMedium.dp) // Space between the icon and the text
                )
            }
        }
    }
}

