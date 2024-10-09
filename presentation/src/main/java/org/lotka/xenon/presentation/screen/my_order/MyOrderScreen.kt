package org.lotka.xenon.presentation.screen.my_order

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import org.lotka.xenon.presentation.compose.StandardTopBar

@Composable
fun MyOrderScreen(
  onNavigateUp:()->Unit={}
){

        Column(modifier = Modifier.fillMaxWidth()) {
            StandardTopBar(
                showArrowBackIosNew = true,
                onNavigateUp = onNavigateUp,
                title = {
                    Text(
                        text = "My Order Screen",
                        style = MaterialTheme.typography.h1,
                        color = MaterialTheme.colors.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                }
            ){


            }
    }
 }