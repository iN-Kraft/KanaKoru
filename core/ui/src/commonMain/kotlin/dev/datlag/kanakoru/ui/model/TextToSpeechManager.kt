package dev.datlag.kanakoru.ui.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import dev.datlag.kommons.locale.Japan
import dev.datlag.kommons.locale.Locale
import kotlinx.collections.immutable.toImmutableList
import nl.marc_apps.tts.TextToSpeechEngine
import nl.marc_apps.tts.TextToSpeechInstance
import nl.marc_apps.tts.Voice
import nl.marc_apps.tts.experimental.ExperimentalVoiceApi
import nl.marc_apps.tts.rememberTextToSpeechOrNull

@OptIn(ExperimentalVoiceApi::class)
class TextToSpeechManager(
    val googleTTS: TextToSpeechInstance?,
    val systemTTS: TextToSpeechInstance?,
    val deviceOnline: Boolean
) {

    val googleJapaneseVoices by derivedStateOf {
        googleTTS?.voices.orEmpty().filter {
            isJapanese(it)
        }.toImmutableList()
    }

    val systemJapaneseVoices by derivedStateOf {
        systemTTS?.voices.orEmpty().filter {
            isJapanese(it)
        }.toImmutableList()
    }

    val isAvailable: Boolean by derivedStateOf {
        googleJapaneseVoices.isNotEmpty() || systemJapaneseVoices.isNotEmpty()
    }

    fun enqueue(text: String, clearQueue: Boolean = true) {
        val googleVoice = selectBestVoice(googleJapaneseVoices)
        if (googleVoice != null && googleTTS != null) {
            googleTTS.currentVoice = googleVoice
            googleTTS.enqueue(text, clearQueue)
            return
        }

        val systemVoice = selectBestVoice(systemJapaneseVoices)
        if (systemVoice != null && systemTTS != null) {
            systemTTS.currentVoice = systemVoice
            systemTTS.enqueue(text, clearQueue)
            return
        }
    }

    private fun selectBestVoice(list: List<Voice>): Voice? {
        if (list.isEmpty()) {
            return null
        }

        return list.firstOrNull { voice ->
            voice.isDefault
        } ?: if (deviceOnline) {
            list.firstOrNull { voice ->
                voice.isOnline
            }
        } else {
            list.firstOrNull { voice ->
                !voice.isOnline
            }
        } ?: list.firstOrNull()
    }

    private fun isJapanese(voice: Voice, ): Boolean {
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

    return remember(googleTTS, systemTTS) {
        TextToSpeechManager(googleTTS, systemTTS, deviceOnline = true)
    }
}