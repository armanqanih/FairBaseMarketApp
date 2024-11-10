package org.lotka.xenon.presentation.compose

import androidx.compose.runtime.Composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.padding

import androidx.compose.material.DismissState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState

import androidx.compose.material3.Icon


import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.unit.dp

import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> SwipeToDeleteContainer(
    item: T,
    onDelete: (T) -> Unit,
    animationDuration: Int = 500,
    content: @Composable (T) -> Unit
) {
    var isRemoved by remember { mutableStateOf(false) }

    // Remember the dismiss state for swipe-to-dismiss behavior
    val state = rememberDismissState(
        confirmStateChange = { value ->
            if (value == androidx.compose.material.DismissValue.DismissedToStart) {
                isRemoved = true
                true  // Allow the swipe action
            } else {
                false  // Prevent any other dismiss direction
            }
        }
    )

    // After the item is removed, trigger the delete action with a delay for animation
    LaunchedEffect(key1 = isRemoved) {
        if (isRemoved) {
            delay(animationDuration.toLong())  // Wait for the animation to finish
            onDelete(item)  // Call the delete action
        }
    }

    // Animate visibility and handle dismissal with swipe
    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()  // Apply shrink and fade out animation
    ) {
        androidx.compose.material.SwipeToDismiss(
            state = state,
            background = { DeleteBackground(swipeDismissState = state) },
            dismissContent = { content(item) },
            directions = setOf(androidx.compose.material.DismissDirection.EndToStart)  // Swipe left to delete
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DeleteBackground(
    swipeDismissState: DismissState
) {
    val color = if (swipeDismissState.dismissDirection == androidx.compose.material.DismissDirection.EndToStart) {
        Color.Red  // Red color for delete action
    } else Color.Transparent

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)  // Apply red background when swiped
            .padding(16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            tint = Color.White  // White icon color
        )
    }
}
