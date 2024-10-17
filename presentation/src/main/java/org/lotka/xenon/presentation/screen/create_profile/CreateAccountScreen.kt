package org.lotka.xenon.presentation.screen.create_profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.lotka.xenon.domain.util.Constants
import org.lotka.xenon.presentation.compose.StandardButton

@Composable
fun CreateAccountScreen(

){
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
        ){
        Text(
            text = "You Dont Have An Account",
            style = MaterialTheme.typography.h1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onSurface
        )
        Spacer(modifier = Modifier.height(Constants.SpaceLarge.dp))
        StandardButton(
            modifier = Modifier.align(Alignment.BottomCenter),
            title = "Create An Account"
        )
    }



}