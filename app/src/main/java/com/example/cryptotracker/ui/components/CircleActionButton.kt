package com.example.cryptotracker.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.cryptotracker.ui.theme.AppTheme

/**
 * Circular icon button on a filled surface — ports the Flutter `CircleActionButton`
 * atom: a Material circle with `surfaceCard` background and a `textSecondary`-tinted
 * icon sized to ~46% of the button (matching the Flutter `size * 0.46`).
 *
 * `Surface(onClick = …)` gives the circular ripple for free.
 */
@Composable
fun CircleActionButton(
    onClick: () -> Unit,
    imageVector: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
) {
    Surface(
        onClick = onClick,
        shape = CircleShape,
        color = AppTheme.colors.surfaceCard,
        modifier = modifier.size(size),
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = imageVector,
                contentDescription = contentDescription,
                tint = AppTheme.colors.textSecondary,
                modifier = Modifier.size(size * 0.46f),
            )
        }
    }
}
