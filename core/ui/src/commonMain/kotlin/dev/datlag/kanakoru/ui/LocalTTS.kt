package dev.datlag.kanakoru.ui

import androidx.compose.runtime.compositionLocalOf
import dev.datlag.kanakoru.ui.model.TextToSpeechManager

val LocalTTS = compositionLocalOf<TextToSpeechManager> { error("No TextToSpeechManager initialized") }