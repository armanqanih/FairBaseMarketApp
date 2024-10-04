package org.lotka.xenon.presentation.screen.home

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.lotka.xenon.domain.util.Constants.SpaceMedium
import org.lotka.xenon.presentation.compose.StandardHeaderText
import org.lotka.xenon.presentation.compose.StandardTopBar
import org.lotka.xenon.presentation.screen.home.compose.Categories
import org.lotka.xenon.presentation.screen.home.compose.HeaderSection
import org.lotka.xenon.presentation.screen.home.compose.Recommendation



@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
){
   val state = viewModel.state.collectAsState().value
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
                 IconButton(onClick = { /*TODO*/ },
                     modifier = Modifier
                         .clip(shape = CircleShape)
                         .background(
                             MaterialTheme.colors.onBackground
                         )

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
                    Categories(categories = state.categories)
                }
                item {
                    StandardHeaderText(name = "Recommendations",
                        onSeeAllTextClick = {}) }
                items(state.itemsList.chunked(2)) { rowItems ->

                    // Chunk the list into pairs of two items
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp) // Space between the items
                    ) {
                        // For each item in the row, display it using the Recommendation composable
                        for (item in rowItems) {
                            Recommendation(item, Modifier.weight(1f)) // Equal space for each item
                        }

                        // If there is an odd number of items, fill the remaining space in the last row
                        if (rowItems.size < 2) {
                            Box(modifier = Modifier.weight(1f)) {} // Empty box to fill the remaining space
                        }
                    }

            }
        }

}}}




