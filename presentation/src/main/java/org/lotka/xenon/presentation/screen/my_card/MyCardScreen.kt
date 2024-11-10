package org.lotka.xenon.presentation.screen.my_card

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.lotka.xenon.domain.util.Constants.SpaceMedium
import org.lotka.xenon.domain.util.Constants.SpaceSmall
import org.lotka.xenon.presentation.compose.StandardButton
import org.lotka.xenon.presentation.compose.StandardTopBar
import org.lotka.xenon.presentation.compose.SwipeToDeleteContainer
import org.lotka.xenon.presentation.screen.my_card.compose.ActionIcon
import org.lotka.xenon.presentation.screen.my_card.compose.MyOrderCard
import org.lotka.xenon.presentation.screen.my_card.compose.PaymentMethod
import org.lotka.xenon.presentation.screen.my_card.compose.PricesTextRow
import org.lotka.xenon.presentation.screen.my_card.compose.SwipeableItemWithActions

import org.lotka.xenon.presentation.util.calculateTotalPrice
import org.lotka.xenon.presentation.util.formatPrice

@Composable
fun MyCardScreen(
    viewModel: MyCardViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit = {},
) {
    val state by viewModel.state.collectAsState()
    val items = state.itemCardList.toMutableList()
    val itemCount = items.size

    val deliveryPrice = 10.0

    // Recalculate quantities only when cartItems change
    val quantities = remember(items) { mutableStateListOf(*Array(itemCount) { 1 }) }

    // Calculate total price based on actual item prices and quantities
    var totalPrice by remember {
        mutableStateOf(
            calculateTotalPrice(
                items.map { it.price ?: 0.0 },
                quantities
            ) + deliveryPrice
        )
    }

    // Ensure totalPrice is calculated when the screen is first shown
    LaunchedEffect(items, quantities) {
        totalPrice = calculateTotalPrice(items.map { it.price ?: 0.0 }, quantities) + deliveryPrice
    }


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = SpaceSmall.dp, end = SpaceMedium.dp),
        topBar = {
            StandardTopBar(
                onNavigateUp = onNavigateUp,
                showArrowBackIosNew = true,
                title = {
                    Spacer(modifier = Modifier.width(70.dp))
                    Text(
                        text = "My Cart",
                        style = MaterialTheme.typography.h2,
                        color = MaterialTheme.colors.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold
                    )
                }
            )


        }
    ) {
        if (itemCount == 0) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "You Card Is Empty",
                    style = MaterialTheme.typography.h1,
                    color = MaterialTheme.colors.onSurface,
                )
            }
         } else if (itemCount <= 3) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it), // Use padding for LazyColumn to avoid overlap
                verticalArrangement = Arrangement.spacedBy(SpaceMedium.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(SpaceSmall.dp))
                }

                itemsIndexed(items) { index, item ->
                    SwipeToDeleteContainer(
                        item = item,
                        onDelete = {
                            viewModel.removeItemInCard(item.categoryId.toString())
                        }
                    ) {
                            val itemIndex = items.indexOf(item)
                            if (itemIndex >= 0) {
                                val toolTotalPrice =
                                    formatPrice((item.price ?: 0.0) * quantities[itemIndex])
                                MyOrderCard(
                                    nameOfTool = item.title.toString(),
                                    toolPrice = item.price?.let { formatPrice(it) },
                                    toolTotalPrice = toolTotalPrice,
                                    quantityText = quantities[itemIndex].toString(),
                                    onPlusButtonClick = {
                                        quantities[itemIndex] += 1
                                        totalPrice = calculateTotalPrice(
                                            items.map { it.price ?: 0.0 },
                                            quantities
                                        ) + deliveryPrice
                                    },
                                    onMinusButtonClick = {
                                        if (quantities[itemIndex] > 1) {
                                            quantities[itemIndex] -= 1
                                            totalPrice = calculateTotalPrice(
                                                items.map { it.price ?: 0.0 },
                                                quantities
                                            ) + deliveryPrice
                                        }
                                    },
                                    toolImage = item.picUrl
                                )
                            }
                    }
                }
            }

            // This column will be aligned at the bottom of the screen
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)  // Align the checkout section at the bottom
                    .padding(bottom = SpaceSmall.dp)
            ) {
                PricesTextRow(
                    title = "SubTotal",
                    price = "$${formatPrice(calculateTotalPrice(items.map { it.price ?: 0.0 }, quantities))}"
                )
                PricesTextRow(
                    title = "Delivery",
                    price = "$${formatPrice(deliveryPrice)}"
                )

                Spacer(modifier = Modifier.height(SpaceSmall.dp))
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colors.onBackground
                )
                Spacer(modifier = Modifier.height(SpaceSmall.dp))

                PricesTextRow(
                    title = "Total",
                    price = "$${formatPrice(totalPrice)}"
                )

                Spacer(modifier = Modifier.height(SpaceSmall.dp))
                PaymentMethod()
                Spacer(modifier = Modifier.height(SpaceSmall.dp))
                StandardButton(
                    modifier = Modifier.fillMaxWidth(),
                    title = "Check Out",
                    onButtonClick = {}
                )
            }
        }



        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it), // Make LazyColumn take up available space
                verticalArrangement = Arrangement.spacedBy(SpaceMedium.dp)
            ) {

                item {
                    Spacer(modifier = Modifier.height(SpaceSmall.dp))
                }
                itemsIndexed(items, key = { _, item -> item.categoryId }) { index, item ->
                    SwipeToDeleteContainer(
                        item = item,
                        onDelete = {
                            viewModel.removeItemInCard(item.categoryId.toString())
                        }
                    ) {
                        val itemIndex = items.indexOfFirst { it.categoryId == item.categoryId }

                        if (itemIndex >= 0) {
                            val toolTotalPrice =
                                formatPrice((item.price ?: 0.0) * quantities[itemIndex])
                            MyOrderCard(
                                nameOfTool = item.title.toString(),
                                toolPrice = item.price?.let { formatPrice(it) },
                                toolTotalPrice = toolTotalPrice,
                                quantityText = quantities[itemIndex].toString(),
                                onPlusButtonClick = {
                                    quantities[itemIndex] += 1
                                    totalPrice = calculateTotalPrice(
                                        items.map { it.price ?: 0.0 },
                                        quantities
                                    ) + deliveryPrice
                                },
                                onMinusButtonClick = {
                                    if (quantities[itemIndex] > 1) {
                                        quantities[itemIndex] -= 1
                                        totalPrice = calculateTotalPrice(
                                            items.map { it.price ?: 0.0 },
                                            quantities
                                        ) + deliveryPrice
                                    }
                                },
                                toolImage = item.picUrl
                            )
                        }
                    }}



            item {
                    PricesTextRow(
                        title = "SubTotal",
                        price = "$${
                            formatPrice(
                                calculateTotalPrice(
                                    items.map { it.price ?: 0.0 },
                                    quantities
                                )
                            )
                        }"
                    )
                    PricesTextRow(
                        title = "Delivery",
                        price = "$${formatPrice(deliveryPrice)}"
                    )

                    Spacer(modifier = Modifier.height(SpaceSmall.dp))
                    Divider(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colors.onBackground
                    )
                    Spacer(modifier = Modifier.height(SpaceSmall.dp))

                    PricesTextRow(
                        title = "Total",
                        price = "$${formatPrice(totalPrice)}"
                    )

                    Spacer(modifier = Modifier.height(SpaceSmall.dp))
                    PaymentMethod()
                    Spacer(modifier = Modifier.height(SpaceSmall.dp))
                    StandardButton(
                        modifier = Modifier.padding(bottom = SpaceSmall.dp),
                        title = "Check Out",
                        onButtonClick = {}
                    )
                }
            }
        }
    }
}






