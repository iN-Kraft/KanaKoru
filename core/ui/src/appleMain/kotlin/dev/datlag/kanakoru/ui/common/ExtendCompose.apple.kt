package dev.datlag.kanakoru.ui.common

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import dev.jordond.connectivity.ConnectivityOptions
import dev.jordond.connectivity.compose.ConnectivityState
import dev.jordond.connectivity.compose.rememberConnectivityState

@Composable
actual fun rememberPlatformConnectivity(
    block: ConnectivityOptions.Builder.() -> Unit
): ConnectivityState {
    return rememberConnectivityState(block = block)
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
actual fun calculateWindowSizeClass(): WindowSizeClass {
    return androidx.compose.material3.windowsizeclass.calculateWindowSizeClass()
}