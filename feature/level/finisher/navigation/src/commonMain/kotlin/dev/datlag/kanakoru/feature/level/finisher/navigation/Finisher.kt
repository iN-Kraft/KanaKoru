package dev.datlag.kanakoru.feature.level.finisher.navigation

import dev.datlag.kanakoru.model.JapaneseChar
import dev.datlag.kanakoru.model.LevelNavKey
import kotlinx.serialization.Serializable

@Serializable
data class Finisher(
    val japaneseChar: JapaneseChar,
    val completionRatio: Float = 0.6F
) : LevelNavKey {

    override val singleStrokeSupport: Boolean = false
    override val multiStrokeSupport: Boolean = true
}
