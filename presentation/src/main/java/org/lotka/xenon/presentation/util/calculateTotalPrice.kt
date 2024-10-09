package org.lotka.xenon.presentation.util

fun calculateTotalPrice(prices: List<Double>, quantities: List<Int>): Double {
  var total = 0.0
  for (i in prices.indices) {
    total += prices[i] * quantities[i]
  }
  return total
}
