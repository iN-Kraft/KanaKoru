package dev.datlag.kanakoru.feature.level

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import dev.datlag.kanakoru.feature.level.guidedtour.featureLevelGuidedTour
import dev.datlag.kanakoru.feature.level.guidedtour.navigation.GuidedTour
import dev.datlag.kanakoru.model.JapaneseChar
import dev.datlag.kanakoru.ui.common.rememberNavBackStack

@Composable
fun LevelScreen(char: JapaneseChar, onBack: () -> Unit) {
    val backStack = rememberNavBackStack(LevelSerialization.configuration, GuidedTour(char))

    NavDisplay(
        modifier = Modifier.fillMaxSize(),
        backStack = backStack,
        entryProvider = entryProvider {
            featureLevelGuidedTour(onBack = {
                onBack()
            }, onNext = {

            })
        },
        onBack = onBack
    )
}