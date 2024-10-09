package org.lotka.xenon.presentation.screen.my_card

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.lotka.xenon.domain.util.Constants.SpaceMedium
import org.lotka.xenon.domain.util.Constants.SpaceSmall
import org.lotka.xenon.presentation.compose.StandardButton
import org.lotka.xenon.presentation.compose.StandardTopBar
import org.lotka.xenon.presentation.screen.my_card.compose.MyOrderCard
import org.lotka.xenon.presentation.screen.my_card.compose.PaymentMethod
import org.lotka.xenon.presentation.screen.my_card.compose.PricesTextRow
import org.lotka.xenon.presentation.screen.my_card.compose.calculateTotalPrice
import org.lotka.xenon.presentation.util.formatPrice

@Composable
fun MyCardScreen(onNavigateUp: () -> Unit = {}) {
    val initialPrice = 500.0
    val deliveryPrice = 10.0
    val itemCount = 4 // Set this to the actual count of items in the cart
    val quantities =
        remember { mutableStateListOf(*Array(itemCount) { 1 }) } // List to track quantities of each item
    val prices = List(itemCount) { initialPrice } // Assume all items have the same initial price

    // Calculate total price initially based on initial prices and quantities
    var totalPrice by remember {
        mutableStateOf(
            calculateTotalPrice(
                prices,
                quantities
            ) + deliveryPrice
        )
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

        if (itemCount <= 3) {
            Box(modifier = Modifier.fillMaxSize()) {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it), // Make LazyColumn take up available space
                    verticalArrangement = Arrangement.spacedBy(SpaceMedium.dp)
                ) {
                    items(itemCount) { index ->
                        Spacer(modifier = Modifier.height(SpaceMedium.dp))
                        MyOrderCard(
                            nameOfTool = "laptop",
                            toolPrice = formatPrice(prices[index]), // Format the price
                            toolTotalPrice = formatPrice(prices[index] * quantities[index]), // Format the total price
                            quantityText = quantities[index].toString(),
                            onPlusButtonClick = {
                                quantities[index] += 1
                                totalPrice = calculateTotalPrice(prices, quantities) + deliveryPrice
                            },
                            onMinusButtonClick = {
                                if (quantities[index] > 1) {
                                    quantities[index] -= 1
                                    totalPrice =
                                        calculateTotalPrice(prices, quantities) + deliveryPrice
                                }
                            }
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter) // Align this column at the bottom
                        .fillMaxWidth()
                        .padding(bottom = SpaceMedium.dp)
                ) {
                    PricesTextRow(
                        title = "SubTotal",
                        price = "$${
                            formatPrice(
                                calculateTotalPrice(
                                    prices,
                                    quantities
                                )
                            )
                        }" // Format the subtotal
                    )
                    PricesTextRow(
                        title = "Delivery",
                        price = "$${formatPrice(deliveryPrice)}" // Format the delivery price
                    )

                    Spacer(modifier = Modifier.height(SpaceSmall.dp))
                    Divider(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colors.onBackground
                    )
                    Spacer(modifier = Modifier.height(SpaceSmall.dp))

                    PricesTextRow(
                        title = "Total",
                        price = "$${formatPrice(totalPrice)}" // Format the total price
                    )

                    Spacer(modifier = Modifier.height(SpaceSmall.dp))
                    PaymentMethod()
                    Spacer(modifier = Modifier.height(SpaceSmall.dp))
                    StandardButton(
                        title = "Check Out",
                        onButtonClick = {}
                    )
                }


            }


        } else if (itemCount >= 4) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it), // Make LazyColumn take up available space
                verticalArrangement = Arrangement.spacedBy(SpaceMedium.dp)
            ) {

                items(itemCount) { index ->
                    Spacer(modifier = Modifier.height(SpaceMedium.dp))
                    MyOrderCard(
                        nameOfTool = "laptop",
                        toolPrice = formatPrice(prices[index]), // Format the price
                        toolTotalPrice = formatPrice(prices[index] * quantities[index]), // Format the total price
                        quantityText = quantities[index].toString(),
                        onPlusButtonClick = {
                            quantities[index] += 1
                            totalPrice = calculateTotalPrice(prices, quantities) + deliveryPrice
                        },
                        onMinusButtonClick = {
                            if (quantities[index] > 1) {
                                quantities[index] -= 1
                                totalPrice = calculateTotalPrice(prices, quantities) + deliveryPrice
                            }
                        }
                    )
                }
                item {
                    PricesTextRow(
                        title = "SubTotal",
                        price = "$${
                            formatPrice(
                                calculateTotalPrice(
                                    prices,
                                    quantities
                                )
                            )
                        }" // Format the subtotal
                    )
                    PricesTextRow(
                        title = "Delivery",
                        price = "$${formatPrice(deliveryPrice)}" // Format the delivery price
                    )

                    Spacer(modifier = Modifier.height(SpaceSmall.dp))
                    Divider(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colors.onBackground
                    )
                    Spacer(modifier = Modifier.height(SpaceSmall.dp))

                    PricesTextRow(
                        title = "Total",
                        price = "$${formatPrice(totalPrice)}" // Format the total price
                    )

                    Spacer(modifier = Modifier.height(SpaceSmall.dp))
                    PaymentMethod()
                    Spacer(modifier = Modifier.height(SpaceSmall.dp))
                    StandardButton(
                        title = "Check Out",
                        onButtonClick = {}
                    )
                }


            }


        }
    }

}






