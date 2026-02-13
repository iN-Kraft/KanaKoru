package dev.datlag.kanakoru.feature.level

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import dev.datlag.kanakoru.feature.level.guidedtour.featureLevelGuidedTour
import dev.datlag.kanakoru.feature.level.guidedtour.navigation.GuidedTour
import dev.datlag.kanakoru.model.JapaneseChar
import dev.datlag.kanakoru.model.LevelNavKey
import dev.datlag.kanakoru.ui.LevelScaffold
import dev.datlag.kanakoru.ui.common.rememberNavBackStack
import dev.datlag.kanakoru.ui.model.CanvasChar
import dev.datlag.kanakoru.ui.model.rememberDollarNCanvasState

@Composable
fun LevelScreen(char: JapaneseChar) {
    val backStack = rememberNavBackStack(LevelSerialization.configuration, GuidedTour(char))

    NavDisplay(
        modifier = Modifier.fillMaxSize(),
        backStack = backStack,
        entryProvider = entryProvider {
            featureLevelGuidedTour(backStack)
        }
    )
}