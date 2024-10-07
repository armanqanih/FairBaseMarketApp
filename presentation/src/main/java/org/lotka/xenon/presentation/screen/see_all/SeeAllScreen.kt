package org.lotka.xenon.presentation.screen.see_all

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import kotlinx.coroutines.flow.Flow
import org.lotka.xenon.domain.model.Items
import org.lotka.xenon.domain.util.Constants.CAMERA
import org.lotka.xenon.domain.util.Constants.CONSOLE
import org.lotka.xenon.domain.util.Constants.HEADPHONE
import org.lotka.xenon.domain.util.Constants.PC
import org.lotka.xenon.domain.util.Constants.SMARTPHONE
import org.lotka.xenon.domain.util.Constants.SMARTWATCH
import org.lotka.xenon.domain.util.Constants.SpaceLarge
import org.lotka.xenon.domain.util.Constants.SpaceMedium
import org.lotka.xenon.domain.util.Constants.SpaceSmall
import org.lotka.xenon.domain.util.Resource
import org.lotka.xenon.presentation.compose.StandardTopBar
import org.lotka.xenon.presentation.screen.home.HomeViewModel
import org.lotka.xenon.presentation.screen.see_all.compose.SeeAllItem

@Composable
fun SeeAllScreen(
    viewModel: SeeAllViewModel = hiltViewModel(),
    selectedCatgory: String,
    onNavigateUp: () -> Unit = {},
) {

    val state = viewModel.state.collectAsState().value
    val itemList = state.itemsList.collectAsLazyPagingItems()

    var title = ""

    when (selectedCatgory) {
        PC -> {  title = "Pc"   }
        SMARTPHONE -> { title = "Smartphone" }
        HEADPHONE -> { title = "Headphone" }
        CONSOLE -> { title = "Console"  }
        CAMERA -> { title = "Camera" }
        SMARTWATCH -> { title = "smartwatch" }
    }


    Column(modifier = Modifier.fillMaxWidth()) {
        StandardTopBar(
            showArrowBackIosNew = true,
            onNavigateUp = onNavigateUp,
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h1,
                    color = MaterialTheme.colors.onSurface,
                    fontWeight = FontWeight.Bold
                )
            }
        )
        Spacer(modifier = Modifier.height(SpaceSmall.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(SpaceSmall.dp),
            horizontalArrangement = Arrangement.spacedBy(SpaceMedium.dp),
            verticalArrangement = Arrangement.spacedBy(SpaceMedium.dp)
        ) {
            items(itemList.itemCount) { index ->
                itemList[index]?.let { SeeAllItem(items = it) }
            }

        }


    }

}