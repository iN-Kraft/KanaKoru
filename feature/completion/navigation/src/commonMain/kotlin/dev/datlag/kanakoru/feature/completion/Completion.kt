package dev.datlag.kanakoru.feature.completion

import androidx.navigation3.runtime.NavKey
import dev.datlag.kanakoru.model.JapaneseChar
import kotlinx.serialization.Serializable

@Serializable
data class Completion(
    val japaneseChar: JapaneseChar
) : NavKey
