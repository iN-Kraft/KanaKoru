package dev.datlag.kanakoru.feature.level

import androidx.lifecycle.ViewModel
import androidx.navigation3.runtime.NavKey
import dev.datlag.inkraft.typeOf
import dev.datlag.kanakoru.feature.level.guidedtour.navigation.GuidedTour
import dev.datlag.kanakoru.feature.level.tracer.navigation.Tracer
import dev.datlag.kanakoru.feature.level.trainingwheels.navigation.TrainingWheels
import dev.datlag.kanakoru.model.JapaneseChar
import dev.datlag.kanakoru.model.LevelNavKey
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

class LevelViewModel(
    private val character: JapaneseChar
) : ViewModel() {

    private val isMultiStroke: Boolean = character.path.data.size > 1

    private val allLevels = persistentListOf(
        GuidedTour(character),
        TrainingWheels(character),
        Tracer(character)
    )

    private val levelSequence = allLevels.filter { level ->
        if (isMultiStroke) {
            level.multiStrokeSupport
        } else {
            level.singleStrokeSupport
        }
    }.toImmutableList()

    val startDestination = levelSequence.first()

    fun getNextRoute(currentRoute: NavKey): LevelNavKey? {
        val currentIndex = levelSequence.indexOfFirst {
            it::class == currentRoute::class
        }.takeUnless { it < 0 } ?: levelSequence.indexOfFirst {
            it::class typeOf currentRoute::class
        }

        return levelSequence.getOrNull(currentIndex + 1)
    }

}