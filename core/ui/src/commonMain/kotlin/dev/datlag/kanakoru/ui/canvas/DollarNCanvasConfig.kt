package dev.datlag.kanakoru.ui.canvas

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Stable
class DollarNCanvasConfig(
    initialShowStartingPoints: Boolean,
    initialShowOrder: Boolean,
    initialShowTemplate: Boolean,
    initialShowGrid: Boolean
) {

    var showStartingPoints by mutableStateOf(initialShowStartingPoints)
    var showOrder by mutableStateOf(initialShowOrder)
    var showTemplate by mutableStateOf(initialShowTemplate)
    var showGrid by mutableStateOf(initialShowGrid)

    fun toggleGrid() {
        showGrid = !showGrid
    }

    fun toggleTemplate() {
        showTemplate = !showTemplate
    }

    fun hideHints() {
        showStartingPoints = false
        showOrder = false
    }

    internal val forceSize: Boolean
        get() = showStartingPoints || showOrder || showTemplate
}

@Composable
fun rememberDollarNCanvasConfig(
    showStartingPoints: Boolean = true,
    showOrder: Boolean = true,
    showTemplate: Boolean = true,
    showGrid: Boolean = false
): DollarNCanvasConfig {
    return remember(showStartingPoints, showOrder, showTemplate, showGrid) {
        DollarNCanvasConfig(
            initialShowStartingPoints = showStartingPoints,
            initialShowOrder = showOrder,
            initialShowTemplate = showTemplate,
            initialShowGrid = showGrid
        )
    }
}