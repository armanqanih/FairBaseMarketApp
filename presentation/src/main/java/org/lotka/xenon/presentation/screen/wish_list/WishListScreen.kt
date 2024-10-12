package org.lotka.xenon.presentation.screen.wish_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.lotka.xenon.domain.util.Constants.SpaceMedium
import org.lotka.xenon.domain.util.Constants.SpaceSmall
import org.lotka.xenon.presentation.compose.StandardTopBar
import org.lotka.xenon.presentation.screen.wish_list.compose.WishListCard
import org.lotka.xenon.presentation.util.formatPrice

@Composable
fun WishListScreen(
    wishListViewModel: WishListViewModel = hiltViewModel(),
    onNavigateUp:()->Unit={}
){
   val state by wishListViewModel.state.collectAsState()
    val itemList = state.wishListItem

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            StandardTopBar(
                showArrowBackIosNew = true,
                onNavigateUp = onNavigateUp,
                title = {
                    Text(
                        text = "WishList",
                        style = MaterialTheme.typography.h1,
                        color = MaterialTheme.colors.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
        ) {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(it)
            .padding(horizontal = SpaceSmall.dp),
            verticalArrangement = Arrangement.spacedBy(SpaceMedium.dp)
            ) {
            items(itemList){item->
                Spacer(modifier = Modifier.height(SpaceMedium.dp))
                WishListCard(
                    toolImage = item.picUrl.toString(),
                    nameOfTool = item.title.toString(),
                    toolPrice = item.price?.let { formatPrice(it) },
                     rating = item.rating.toString()
                )
            }
        }
    }



    }
