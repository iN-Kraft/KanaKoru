package dev.datlag.kanakoru

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import dev.datlag.kanakoru.feature.NavSerialization
import dev.datlag.kanakoru.feature.home.featureHome
import dev.datlag.kanakoru.feature.home.navigation.Home
import dev.datlag.kanakoru.feature.kana.featureKana
import dev.datlag.kanakoru.feature.level.featureLevel
import dev.datlag.kanakoru.ui.LocalTTS
import dev.datlag.kanakoru.ui.common.rememberNavBackStack
import nl.marc_apps.tts.TextToSpeechEngine
import nl.marc_apps.tts.rememberTextToSpeechOrNull

@Composable
fun Root() {
    val backStack = rememberNavBackStack(NavSerialization.configuration, Home)
    val tts = rememberTextToSpeechOrNull(TextToSpeechEngine.Google)

    CompositionLocalProvider(
        LocalTTS provides tts
    ) {
        NavDisplay(
            modifier = Modifier.fillMaxSize(),
            backStack = backStack,
            entryProvider = entryProvider {
                featureHome(backStack)
                featureKana(backStack)
                featureLevel(backStack)
            }
        )
    }
}