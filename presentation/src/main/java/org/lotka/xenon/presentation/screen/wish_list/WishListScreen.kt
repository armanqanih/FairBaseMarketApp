package org.lotka.xenon.presentation.screen.wish_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import org.lotka.xenon.presentation.compose.StandardTopBar

@Composable
fun WishListScreen(
    onNavigateUp:()->Unit={}
){

        Column(modifier = Modifier.fillMaxWidth()) {
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
            ){

    }}
}