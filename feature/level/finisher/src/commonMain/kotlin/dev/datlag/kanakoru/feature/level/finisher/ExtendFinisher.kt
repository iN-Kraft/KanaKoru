package dev.datlag.kanakoru.feature.level.finisher

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.datlag.kanakoru.feature.level.finisher.navigation.Finisher

fun EntryProviderScope<NavKey>.featureLevelFinisher(onBack: () -> Unit, onNext: () -> Unit) {
    entry<Finisher> {
        FinisherScreen(
            japaneseChar = it.japaneseChar,
            completionRatio = it.completionRatio,
            onBack = onBack,
            onFinish = onNext
        )
    }
}