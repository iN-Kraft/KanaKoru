package dev.datlag.kanakoru.feature.kana.navigation

import androidx.navigation3.runtime.NavKey
import dev.datlag.kanakoru.model.JapaneseChar
import kotlinx.serialization.Serializable

@Serializable
sealed interface Kana : NavKey {

    @Serializable
    data object Hiragana : Kana

    @Serializable
    data object Katakana : Kana

    @Serializable
    data class Draw(val char: JapaneseChar) : NavKey

}
