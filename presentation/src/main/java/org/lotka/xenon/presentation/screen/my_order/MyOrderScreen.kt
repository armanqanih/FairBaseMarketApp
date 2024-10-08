package org.lotka.xenon.presentation.screen.my_order

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.lotka.xenon.domain.util.Constants.SpaceMedium
import org.lotka.xenon.domain.util.Constants.SpaceSmall
import org.lotka.xenon.domain.util.Constants.SpaceToLarge
import org.lotka.xenon.presentation.compose.StandardButton
import org.lotka.xenon.presentation.compose.StandardTopBar
import org.lotka.xenon.presentation.screen.my_order.compose.MyOrderCard
import org.lotka.xenon.presentation.screen.my_order.compose.PaymentMethod
import org.lotka.xenon.presentation.screen.my_order.compose.PricesTextRow
import org.lotka.xenon.presentation.screen.my_order.compose.calculateTotalPrice

@Composable
fun MyOrderScreen(
  onNavigateUp: () -> Unit = {}
) {
  val initialPrice = 500.0
  val deliveryPrice = 10.0
  var totalPrice by remember { mutableStateOf(0.0) }
  val itemCount = 4 // Let's assume you have 5 items in the cart for now
  val quantities = remember { mutableStateListOf(1, 1, 1, 1, 1) } // List to track quantities of each item
  val prices = List(itemCount) { initialPrice } // Assume all items have the same initial price, modify this as per your logic

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(SpaceMedium.dp),
    verticalArrangement = Arrangement.spacedBy(SpaceMedium.dp)
  ) {
    StandardTopBar(
      onNavigateUp = onNavigateUp,
      showArrowBackIosNew = true,
      title = {
        Spacer(modifier = Modifier.width(80.dp))
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

    LazyColumn(
      modifier = Modifier.fillMaxSize(),
      verticalArrangement = Arrangement.spacedBy(SpaceMedium.dp)
    ) {
      items(itemCount) { index ->
        MyOrderCard(
          nameOfTool = "laptop",
          toolPrice = prices[index].toString(),
          toolTotalPrice = (prices[index] * quantities[index]).toString(),
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
        PricesTextRow(title = "SubTotal", price = "$${calculateTotalPrice(prices, quantities)}")
        PricesTextRow(title = "Delivery", price = "$$deliveryPrice")

        Spacer(modifier = Modifier.height(SpaceSmall.dp))
        Divider(modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colors.onBackground)
        Spacer(modifier = Modifier.height(SpaceSmall.dp))

        PricesTextRow(title = "Total", price = "$${totalPrice}")
      }
      item {
        PaymentMethod()
      }
      item {
        StandardButton(
          title = "Check Out",
          onButtonClick = {}
        )
      }

    }
  }
}


