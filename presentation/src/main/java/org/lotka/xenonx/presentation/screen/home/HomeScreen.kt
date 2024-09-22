package org.lotka.xenonx.presentation.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.lotka.xenonx.presentation.screen.home.compose.HomeNewsItem
import org.lotka.xenonx.presentation.util.dimens.SpaceLarge
import org.lotka.xenonx.presentation.util.dimens.SpaceSmall

@Composable
fun HomeScreen(
    onNavigateToDetailScreen: (String) -> Unit,
){

    Scaffold(
        modifier = Modifier.fillMaxSize()
        , topBar = {
            TopAppBar(
                title = {
                    Text(text = "BBC News",
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onBackground,
                        modifier = Modifier.padding(SpaceSmall),

                        )

                },
                actions = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "",
                        tint = MaterialTheme.colors.onBackground
                        )
                }
            )
        }
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)){

            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // 3 items per row
                modifier = Modifier
                    .fillMaxSize()
                    .padding(SpaceLarge),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(10) { index -> // Adjust the item count as needed
                    HomeNewsItem(
                        onNavigateToDetail =  onNavigateToDetailScreen)
                }

            }

        }
    }

}