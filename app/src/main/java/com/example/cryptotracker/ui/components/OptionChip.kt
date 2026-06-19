package com.example.cryptotracker.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cryptotracker.ui.theme.AppTheme

/**
 * Pill-shaped single-choice chip — ports the Flutter `OptionChip` atom:
 * `accent` fill + bold text when selected, otherwise transparent with a divider
 * border. Colors animate on selection (the Flutter `AnimatedContainer`).
 */
@Composable
fun OptionChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(24.dp)
    val background by animateColorAsState(
        if (selected) AppTheme.colors.accent else Color.Transparent,
        tween(150), label = "chipBackground",
    )
    val borderColor by animateColorAsState(
        if (selected) AppTheme.colors.accent else AppTheme.colors.divider,
        tween(150), label = "chipBorder",
    )
    val contentColor by animateColorAsState(
        if (selected) AppTheme.colors.onAccent else AppTheme.colors.textSecondary,
        tween(150), label = "chipContent",
    )

    Text(
        text = label,
        color = contentColor,
        style = AppTheme.typography.body.copy(
            fontWeight = if (selected) FontWeight.W700 else FontWeight.W500,
            fontSize = 14.sp,
        ),
        modifier = modifier
            .clip(shape)
            .background(background)
            .border(1.dp, borderColor, shape)
            .clickable(onClick = onClick)
            .padding(horizontal = 18.dp, vertical = 10.dp),
    )
}
