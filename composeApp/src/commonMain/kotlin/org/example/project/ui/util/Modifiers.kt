package org.example.project.ui.util

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass

// Константы для адаптивных отступов
private const val EXPANDED_HORIZONTAL_PADDING = WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND / 4
private const val COMPACT_HORIZONTAL_PADDING = 4

fun Modifier.adaptiveHorizontalPadding(windowSizeClass: WindowSizeClass): Modifier {
    val paddingDp = when {
        windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND) -> {
            EXPANDED_HORIZONTAL_PADDING
        }
        windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND) -> {
            // Плавный переход между Medium и Expanded
            val factor = (windowSizeClass.minWidthDp - WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND).toFloat() /
                    (WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND - WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)
            (COMPACT_HORIZONTAL_PADDING * (1 - factor) + EXPANDED_HORIZONTAL_PADDING * factor).toInt()
        }
        else -> COMPACT_HORIZONTAL_PADDING
    }
    return this.padding(horizontal = paddingDp.dp)
}