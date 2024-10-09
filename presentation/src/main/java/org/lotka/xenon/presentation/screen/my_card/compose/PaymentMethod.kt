package org.lotka.xenon.presentation.screen.my_card.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.lotka.xenon.domain.util.Constants.SpaceMedium
import org.lotka.xenon.presentation.R
@Composable
fun PaymentMethod() {
    // Track the selected payment method using a state variable
    var selectedPaymentMethod by remember { mutableStateOf("Cash") }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(SpaceMedium.dp)
    ) {
        Text(
            text = "Payment Method",
            style = MaterialTheme.typography.body1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colors.onSurface,
            fontWeight = FontWeight.SemiBold
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            // Cash Payment Card
            PaymentCard(
                modifier = Modifier.weight(1f),
                image = painterResource(id = R.drawable.cash),
                title = "Cash",
                description = "Pay Cash when the medicine arrives at the destination",
                isCardSelected = selectedPaymentMethod == "Cash", // Highlight if selected
                onCardClick = {
                    selectedPaymentMethod = "Cash" // Set as selected
                }
            )

            // Bank Transfer Payment Card
            PaymentCard(
                modifier = Modifier.weight(1f),
                image = painterResource(id = R.drawable.bank),
                title = "Bank Transfer",
                description = "Login to your online account and make a payment",
                isCardSelected = selectedPaymentMethod == "Bank Transfer", // Highlight if selected
                onCardClick = {
                    selectedPaymentMethod = "Bank Transfer" // Set as selected
                }
            )

        }
    }
}
