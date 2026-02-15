package dev.datlag.kanakoru.ui.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.max
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.savedstate.serialization.SavedStateConfiguration
import dev.datlag.kanakoru.ui.Constants
import dev.datlag.kanakoru.ui.NavBackStack
import dev.jordond.connectivity.Connectivity
import dev.jordond.connectivity.ConnectivityOptions
import dev.jordond.connectivity.compose.ConnectivityState
import dev.jordond.connectivity.compose.rememberConnectivityState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope

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
): ImmutableList<Color> = remember(colorScheme) {
    persistentListOf(colorScheme.primary, colorScheme.secondary, colorScheme.tertiary)
}

@Composable
fun rememberNavBackStack(
    configuration: SavedStateConfiguration,
    vararg elements: NavKey
): NavBackStack<NavKey> {
    return rememberNavBackStack(
        configuration = configuration,
        key = null,
        elements = elements
    )
}

@Composable
fun rememberNavBackStack(
    configuration: SavedStateConfiguration,
    key: Any?,
    vararg elements: NavKey
): NavBackStack<NavKey> {
    val baseBackStack = rememberNavBackStack(configuration, *elements)
    return remember(baseBackStack, key) {
        NavBackStack(baseBackStack)
    }
}

@Composable
operator fun PaddingValues.plus(other: PaddingValues): PaddingValues {
    val direction = LocalLayoutDirection.current

    return PaddingValues(
        start = this.calculateStartPadding(direction) + other.calculateStartPadding(direction),
        top = this.calculateTopPadding() + other.calculateTopPadding(),
        end = this.calculateEndPadding(direction) + other.calculateEndPadding(direction),
        bottom = this.calculateBottomPadding() + other.calculateBottomPadding()
    )
}

@Composable
operator fun PaddingValues.plus(all: Dp): PaddingValues {
    val direction = LocalLayoutDirection.current
    val other = PaddingValues(all)

    return PaddingValues(
        start = this.calculateStartPadding(direction) + other.calculateStartPadding(direction),
        top = this.calculateTopPadding() + other.calculateTopPadding(),
        end = this.calculateEndPadding(direction) + other.calculateEndPadding(direction),
        bottom = this.calculateBottomPadding() + other.calculateBottomPadding()
    )
}

@Composable
fun PaddingValues.merge(other: PaddingValues): PaddingValues {
    val direction = LocalLayoutDirection.current

    return PaddingValues(
        start = max(this.calculateStartPadding(direction), other.calculateStartPadding(direction)),
        top = max(this.calculateTopPadding(), other.calculateTopPadding()),
        end = max(this.calculateEndPadding(direction), other.calculateEndPadding(direction)),
        bottom = max(this.calculateBottomPadding(), other.calculateBottomPadding())
    )
}

@Composable
fun PaddingValues.merge(all: Dp): PaddingValues {
    val direction = LocalLayoutDirection.current
    val other = PaddingValues(all)

    return PaddingValues(
        start = max(this.calculateStartPadding(direction), other.calculateStartPadding(direction)),
        top = max(this.calculateTopPadding(), other.calculateTopPadding()),
        end = max(this.calculateEndPadding(direction), other.calculateEndPadding(direction)),
        bottom = max(this.calculateBottomPadding(), other.calculateBottomPadding())
    )
}

fun LazyGridScope.header(
    content: @Composable LazyGridItemScope.() -> Unit
) {
    item(span = { GridItemSpan(this.maxLineSpan) }, content = content)
}

@Composable
expect fun rememberPlatformConnectivity(
    block: ConnectivityOptions.Builder.() -> Unit
): ConnectivityState