package org.lotka.xenon.presentation.screen.explore


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.lotka.xenon.domain.model.toWishListModel
import org.lotka.xenon.domain.util.Constants.SpaceMedium
import org.lotka.xenon.presentation.compose.StandardHeaderText
import org.lotka.xenon.presentation.compose.StandardTopBar
import org.lotka.xenon.presentation.screen.explore.compose.Categories
import org.lotka.xenon.presentation.screen.explore.compose.HeaderSection
import org.lotka.xenon.presentation.screen.explore.compose.Recommendation
import org.lotka.xenon.presentation.util.UiEvent


@Composable
fun ExploreScreen(
    viewModel: ExploreViewModel = hiltViewModel(),
    onNavigateToSeeAll:(String)-> Unit = {},
    onNavigateToDetail:(String)-> Unit = {}
){
    val state = viewModel.state.collectAsState().value
    val isFavorite = state.isFavorite

    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is UiEvent.ShowSnakeBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                UiEvent.onNavigateUp -> {

                }
            }
        }
    }





    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            StandardTopBar(
                modifier = Modifier.fillMaxWidth()
                , title = {
                    Column {
                        Text(text = "WellCome Back",
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.onSurface,
                            fontWeight = FontWeight.Medium
                            )
                        Text(text = "Arman",
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.onSurface,
                            fontWeight = FontWeight.Bold
                            )
                    }
                },
                actions = {
                 IconButton(onClick = {

                 },
                          modifier = Modifier
                         .clip(shape = CircleShape)
                         .background(MaterialTheme.colors.onBackground)
                     ) {
                     Icon(imageVector = Icons.Default.NotificationsNone,
                         contentDescription ="",
                         tint = MaterialTheme.colors.onSurface
                     )
                 }
                    Spacer(modifier = Modifier.width(SpaceMedium.dp))
                    
                    IconButton(onClick = {  },
                                 modifier = Modifier
                                .clip(shape = CircleShape)
                                .background(
                                    MaterialTheme.colors.onBackground
                                )

                    ) {
                        Icon(imageVector = Icons.Default.Search,
                            contentDescription ="",
                            tint = MaterialTheme.colors.onSurface
                        )

                    }
                    
                }
            )
        }

        )  {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it),
            contentAlignment = androidx.compose.ui.Alignment.Center
            ){

            if(state.isLoading){
                CircularProgressIndicator()
            }

            if (state.error != null){
                Text(text = state.error,
                    color = MaterialTheme.colors.error
                    )
            }

            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(SpaceMedium.dp)

            ) {
                item {
                    HeaderSection()
                }
                item {
                    Categories(
                        onNavigateToSeeAllScreen = onNavigateToSeeAll ,
                        categories = state.categories)
                }
                item {
                    StandardHeaderText(
                        name = "Recommendations",
                        onSeeAllTextClick = {}) }
                items(state.itemList.chunked(2)) { rowItems ->

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)

                    ) {

                        for (item in rowItems) {
                            Recommendation(
                                modifier = Modifier.weight(1f),
                                onNavigateToDetail = onNavigateToDetail,
                                item = item,
                                isFavorite = state.itemWishList.any { it.categoryId == item.categoryId.toString() } ,
                                onFavoriteButtonClick = {
                                    viewModel.onEvent(ExploreEvent.onFavoreateIconClick, item.toWishListModel())
                                }
                            )
                        }


                        if (rowItems.size < 2) {
                            Box(modifier = Modifier.weight(1f)) {}
                        }

                    }

            }
        }

}}}




