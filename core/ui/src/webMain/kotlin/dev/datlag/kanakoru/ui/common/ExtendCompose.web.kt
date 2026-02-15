package dev.datlag.kanakoru.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import dev.jordond.connectivity.ConnectivityOptions
import dev.jordond.connectivity.compose.ConnectivityState
import dev.jordond.connectivity.compose.rememberConnectivityState

@Composable
actual fun rememberPlatformConnectivity(
    block: ConnectivityOptions.Builder.() -> Unit
): ConnectivityState {
    val options = remember(block) {
        ConnectivityOptions.build(block)
    }
    val scope = rememberCoroutineScope()
    val connectivity = remember(options, scope) {
        WebConnectivity(scope, options)
    }

    return rememberConnectivityState(connectivity, scope)
}