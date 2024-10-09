package org.lotka.xenon.presentation.util

fun formatPrice(price: Double): String {
    return if (price % 1 == 0.0) {
        price.toInt().toString() // Return as integer if no decimal part
    } else {
        String.format("%.2f", price) // Otherwise, return with two decimal places
    }
}