package org.lotka.xenon.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.lotka.xenon.domain.util.Constants.SpaceMedium

@Composable
fun StandardTopBar(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit = {},
    showArrowBackIosNew: Boolean = false,
    showTopBarMenu: Boolean = false,
    showArrowBack: Boolean = false,
    title: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) {

    TopAppBar(
        modifier = modifier.fillMaxWidth(),
        title = title,
        navigationIcon = when {
            showArrowBackIosNew -> {
                {
                    IconButton(onClick = onNavigateUp,
                        modifier = Modifier
                            .clip(shape = CircleShape)
                            .background(
                                MaterialTheme.colors.onBackground
                            )
                        ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "ArrowBack",
                            tint = MaterialTheme.colors.onSurface,
                        )
                    }
                }
            }
            showArrowBack -> {
                {
                    IconButton(onClick = onNavigateUp,

                        ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "ArrowBack",
                            tint = MaterialTheme.colors.onSurface,
                        )
                    }
                }
            }
            showTopBarMenu -> {
                {
                    Icon(
                        modifier = Modifier.clickable {
                            onNavigateUp()
                        }.padding(start = SpaceMedium.dp),
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu",
                        tint = MaterialTheme.colors.onBackground,
                    )
                }
            }
            else -> null // No navigation icon if none are requested
        },
        actions = actions,
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.background,
        elevation = 0.dp
    )
}

