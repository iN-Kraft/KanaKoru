package dev.datlag.kanakoru.ui.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import dev.datlag.kanakoru.ui.common.rememberPlatformConnectivity
import dev.datlag.kommons.locale.Japan
import dev.datlag.kommons.locale.Locale
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import nl.marc_apps.tts.TextToSpeechEngine
import nl.marc_apps.tts.TextToSpeechInstance
import nl.marc_apps.tts.Voice
import nl.marc_apps.tts.experimental.ExperimentalVoiceApi
import nl.marc_apps.tts.rememberTextToSpeechOrNull

@OptIn(ExperimentalVoiceApi::class)
@Stable
class TextToSpeechManager(
    val googleTTS: TextToSpeechInstance?,
    val systemTTS: TextToSpeechInstance?
) : AutoCloseable {

    var googleJapaneseVoices by mutableStateOf<ImmutableList<Voice>>(persistentListOf())
        private set

    var systemJapaneseVoices by mutableStateOf<ImmutableList<Voice>>(persistentListOf())
        private set

    val activeGoogleVoice by derivedStateOf { selectBestVoice(googleJapaneseVoices) }
    val activeSystemVoice by derivedStateOf { selectBestVoice(systemJapaneseVoices) }

    val isAvailable: Boolean by derivedStateOf {
        activeGoogleVoice != null || activeSystemVoice != null
    }

    fun updateGoogleVoices(deviceOnline: Boolean) {
        if (googleTTS == null) {
            return
        }

        googleJapaneseVoices = googleTTS.voices.filter {
            isJapanese(it, deviceOnline)
        }.toImmutableList()
    }

    fun updateSystemVoices(deviceOnline: Boolean) {
        if (systemTTS == null) {
            return
        }

        systemJapaneseVoices = systemTTS.voices.filter {
            isJapanese(it, deviceOnline)
        }.toImmutableList()
    }

    fun enqueue(text: String, clearQueue: Boolean = true) {
        val googleVoice = activeGoogleVoice
        if (googleVoice != null && googleTTS != null) {
            if (googleTTS.currentVoice != googleVoice) {
                googleTTS.currentVoice = googleVoice
            }

            googleTTS.enqueue(text, clearQueue)
            return
        }

        val systemVoice = activeSystemVoice
        if (systemVoice != null && systemTTS != null) {
            if (systemTTS.currentVoice != systemVoice) {
                systemTTS.currentVoice = systemVoice
            }

            systemTTS.enqueue(text, clearQueue)
            return
        }
    }

    override fun close() {
        googleTTS?.close()
        systemTTS?.close()
    }

    private fun selectBestVoice(list: List<Voice>): Voice? {
        if (list.isEmpty()) {
            return null
        }

        return list.firstOrNull { voice ->
            voice.isDefault
        } ?: list.firstOrNull()
    }

    private fun isJapanese(voice: Voice, deviceOnline: Boolean): Boolean {
        if (!deviceOnline && voice.isOnline) {
            return false
        }

        val localeByTag = Locale.forLanguageTag(voice.languageTag)
        if (localeByTag?.country is Japan || localeByTag?.language.equals("ja", ignoreCase = true)) {
            return true
        }

        val localeByProperties = Locale(voice.language, voice.region ?: "")
        if (localeByProperties.country is Japan || localeByProperties.language.equals("ja", ignoreCase = true)) {
            return true
        }

        return voice.language.equals("japanese", ignoreCase = true)
    }
}

@Composable
fun rememberTextToSpeechManager(): TextToSpeechManager {
    val googleTTS = rememberTextToSpeechOrNull(TextToSpeechEngine.Google)
    val systemTTS = rememberTextToSpeechOrNull(TextToSpeechEngine.SystemDefault)
    val manager =  remember(googleTTS, systemTTS) {
        TextToSpeechManager(googleTTS, systemTTS)
    }
    val connectivity = rememberPlatformConnectivity {
        autoStart = true
    }

    if (googleTTS != null) {
        LaunchedEffect(googleTTS, connectivity.status) {
            println("Connectivity: ${connectivity.status}")
            manager.updateGoogleVoices(connectivity.isConnected)
        }
    }

    if (systemTTS != null) {
        LaunchedEffect(systemTTS, connectivity.status) {
            manager.updateSystemVoices(connectivity.isConnected)
        }
    }

    DisposableEffect(manager) {
        onDispose {
            manager.close()
        }
    }

    return manager
}