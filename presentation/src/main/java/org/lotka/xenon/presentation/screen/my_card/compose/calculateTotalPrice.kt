package org.lotka.xenon.presentation.screen.my_card.compose

fun calculateTotalPrice(prices: List<Double>, quantities: List<Int>): Double {
  var total = 0.0
  for (i in prices.indices) {
    total += prices[i] * quantities[i]
  }
  return total
}
