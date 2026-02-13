package dev.datlag.kanakoru.feature.level.navigation

import androidx.navigation3.runtime.NavKey
import dev.datlag.kanakoru.model.JapaneseChar
import kotlinx.serialization.Serializable

@Serializable
data class Level(val char: JapaneseChar) : NavKey