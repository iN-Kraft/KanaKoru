package dev.datlag.kanakoru.feature.kana.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Kana : NavKey {

    @Serializable
    data object Hiragana : Kana

    @Serializable
    data object Katakana : Kana

}
