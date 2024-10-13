package org.lotka.xenon.presentation.screen.search


import android.widget.Toast

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement


import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew

import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import org.lotka.xenon.domain.util.Constants.SpaceLarge
import org.lotka.xenon.domain.util.Constants.SpaceMedium
import org.lotka.xenon.domain.util.Constants.SpaceSmall
import org.lotka.xenon.presentation.screen.search.compose.SearchItemCard
import org.lotka.xenon.presentation.ui.navigation.ScreensNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    onNavigateToDetail: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
) {

    val state = viewModel.state.collectAsState().value
    val searchList = state.searchList
    val context = LocalContext.current

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colors.background)
            ) {
                IconButton(onClick = { onNavigateUp() }) {
                    Icon(
                        modifier = Modifier
                            .padding(vertical = SpaceLarge.dp)
                            .align(Alignment.CenterVertically)
                            .clip(shape = CircleShape),
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "BackDetail",
                        tint = MaterialTheme.colors.onSurface,
                    )
                }

                SearchBar(
                    colors = SearchBarDefaults.colors(
                        containerColor = MaterialTheme.colors.background,
                        dividerColor = MaterialTheme.colors.background,
                        inputFieldColors = SearchBarDefaults.inputFieldColors(
                            cursorColor = MaterialTheme.colors.onSurface,
                            focusedTextColor = MaterialTheme.colors.onSurface,

                            )
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    query = state.searchQuery,
                    onQueryChange = {
                        viewModel.onEvent(SearchEvent.UpdateSearchQuery(it))
                    },
                    onSearch = {
                        viewModel.onEvent(SearchEvent.SearchMovies)
                    },
                    enabled = true,
                    placeholder = {
                        Text(
                            text = "Search...",
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.onSurface
                        )
                    },
                    shape = CircleShape,
                    trailingIcon = {
                        Icon(
                            modifier = Modifier,
                            imageVector = Icons.Outlined.Cancel,
                            contentDescription = "Cancel Search",
                            tint = MaterialTheme.colors.onSurface,
                        )

                    },
                    leadingIcon = {
                        Icon(
                            modifier = Modifier,
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon",
                            tint = MaterialTheme.colors.onSurface,
                            )
                    },
                    active = state.searchActive,
                    onActiveChange = {
                        viewModel.onEvent(SearchEvent.UpdateSearchQuery(""))
                    }) {

                }
            }
        }
    ) {

        Spacer(modifier = Modifier.height(SpaceLarge.dp))

        Box(modifier = Modifier.fillMaxSize().padding(it)) {
            if (state.isLoading) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            } else if (!state.error.isNullOrEmpty()) {
                Text(text = state.error, color = MaterialTheme.colors.error, modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn (
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = SpaceMedium.dp)
                    ,
                     verticalArrangement = Arrangement.spacedBy(SpaceMedium.dp)
                ){
                    items(searchList) { item ->

                        SearchItemCard(
                            onNavigateTo = {
                                onNavigateToDetail(
                                    ScreensNavigation.DetailScreen.route+"/${item.categoryId}")
                            },
                            toolImage = item.picUrl!!.firstOrNull(),
                            nameOfTool = item.title ?: "",
                            toolPrice = item.price.toString(),
                            rating = item.rating.toString()
                        )
                    }
                }
            }
        }
    }
}


