package dev.datlag.kanakoru.feature.level.guidedtour

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.datlag.kanakoru.feature.level.guidedtour.navigation.GuidedTour

fun EntryProviderScope<NavKey>.featureLevelGuidedTour(onBack: () -> Unit, onNext: () -> Unit) {
    entry<GuidedTour> {
        GuidedTourScreen(
            japaneseChar = it.japaneseChar,
            onBack = onBack,
            onFinish = onNext
        )
    }
}