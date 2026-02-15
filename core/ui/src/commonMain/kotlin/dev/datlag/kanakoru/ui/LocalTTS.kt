package dev.datlag.kanakoru.ui

import androidx.compose.runtime.compositionLocalOf
import nl.marc_apps.tts.TextToSpeechInstance

val LocalTTS = compositionLocalOf<TextToSpeechInstance?> { null }