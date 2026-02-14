package dev.datlag.kanakoru.feature.level.master.navigation

import dev.datlag.kanakoru.model.JapaneseChar
import dev.datlag.kanakoru.model.LevelNavKey
import kotlinx.serialization.Serializable

@Serializable
data class Master(val japaneseChar: JapaneseChar) : LevelNavKey {

    override val singleStrokeSupport: Boolean = true
    override val multiStrokeSupport: Boolean = true
}
