package dev.datlag.kanakoru.feature.level.guidedtour.navigation

import dev.datlag.kanakoru.model.JapaneseChar
import dev.datlag.kanakoru.model.LevelNavKey
import kotlinx.serialization.Serializable

@Serializable
data class GuidedTour(val japaneseChar: JapaneseChar) : LevelNavKey
