package dev.datlag.kanakoru.feature.level.guidedtour

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.datlag.kanakoru.feature.level.guidedtour.navigation.GuidedTour
import dev.datlag.kanakoru.model.LevelNavKey
import dev.datlag.kanakoru.ui.NavBackStack

fun EntryProviderScope<NavKey>.featureLevelGuidedTour(backStack: NavBackStack<NavKey>) {
    entry<GuidedTour> { GuidedTourScreen(it.japaneseChar) }
}