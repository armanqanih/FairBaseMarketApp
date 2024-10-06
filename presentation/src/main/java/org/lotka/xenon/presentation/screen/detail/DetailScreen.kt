package org.lotka.xenon.presentation.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.lotka.xenon.domain.util.Constants.SpaceLarge
import org.lotka.xenon.domain.util.Constants.SpaceMedium
import org.lotka.xenon.domain.util.Constants.SpaceSmall
import org.lotka.xenon.presentation.compose.StandardTopBar
import org.lotka.xenon.presentation.screen.detail.compose.DetailItem

@Composable
fun DetailScreen(
    viewModel: DetailViewModelViewModel = hiltViewModel(),
) {

val state = viewModel.state.collectAsState().value
    Scaffold(modifier = Modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.background,
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp) // Add a fixed height for bottomBar to prevent it from being pushed off-screen
                    .padding(
                        start = SpaceSmall.dp,
                        end = SpaceSmall.dp
                    )
                    .background(MaterialTheme.colors.background), // Ensure the background color is applied
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(SpaceLarge.dp)
            ) {

                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .padding(start = SpaceMedium.dp)
                        .clip(shape = RoundedCornerShape(SpaceMedium))
                        .height(60.dp)
                        .width(300.dp)
                        .weight(8f)
                ) {
                    Text(
                        text = "Buy Now",
                        color = MaterialTheme.colors.background,
                        style = MaterialTheme.typography.h6
                    )
                }

                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .clip(RoundedCornerShape(SpaceLarge))
                        .size(58.dp)
                        .background(MaterialTheme.colors.onBackground)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ShoppingCart,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onSurface,
                        modifier = Modifier
                            .background(color = MaterialTheme.colors.onBackground)
                            .clip(RoundedCornerShape(SpaceLarge.dp))
                            .fillMaxSize()
                            .padding(SpaceSmall.dp)
                    )
                }

            }

        }, content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(start = SpaceSmall.dp, end = SpaceSmall.dp)
                , verticalArrangement = Arrangement.spacedBy(SpaceMedium.dp)
            ) {
                StandardTopBar(
                    showArrowBackIosNew = true,
                    actions = {
                        IconButton(
                            onClick = { },
                            modifier = Modifier
                                .clip(shape = CircleShape)
                                .background(
                                    MaterialTheme.colors.onBackground
                                )

                        ) {
                            Icon(
                                imageVector = Icons.Default.FavoriteBorder,
                                contentDescription = "",
                                tint = MaterialTheme.colors.onSurface
                            )
                        }
                    }
                )


                LazyColumn() {
                    item {
                        state?.item?.let { it1 -> DetailItem(item = it1) }
                    }
                }


            }

        })


    }











