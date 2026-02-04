package dev.datlag.kanakoru.ui.common

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.savedstate.serialization.SavedStateConfiguration
import dev.datlag.kanakoru.ui.Constants
import dev.datlag.kanakoru.ui.NavBackStack

fun Color.toHexString(): String {
    val a = Constants.colorFloatToInt(this.alpha)
    val r = Constants.colorFloatToInt(this.red)
    val g = Constants.colorFloatToInt(this.green)
    val b = Constants.colorFloatToInt(this.blue)

    return buildString(Constants.COLOR_HEX_LENGTH) {
        append('#')
        append(a.toString(16).padStart(2, '0'))
        append(r.toString(16).padStart(2, '0'))
        append(g.toString(16).padStart(2, '0'))
        append(b.toString(16).padStart(2, '0'))
    }.uppercase()
}

@Composable
internal fun rememberDefaultColors(
    colorScheme: ColorScheme = MaterialTheme.colorScheme
): List<Color> = remember(colorScheme) {
    listOf(colorScheme.primary, colorScheme.secondary, colorScheme.tertiary)
}

@Composable
fun rememberNavBackStack(
    configuration: SavedStateConfiguration,
    vararg elements: NavKey
): NavBackStack<NavKey> {
    val baseBackStack = rememberNavBackStack(configuration, *elements)
    return remember(baseBackStack) {
        NavBackStack(baseBackStack)
    }
}