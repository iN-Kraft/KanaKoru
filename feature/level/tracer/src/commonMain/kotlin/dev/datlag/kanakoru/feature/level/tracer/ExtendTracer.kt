package dev.datlag.kanakoru.feature.level.tracer

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.datlag.kanakoru.feature.level.tracer.navigation.Tracer

fun EntryProviderScope<NavKey>.featureLevelTracer(onBack: () -> Unit, onNext: () -> Unit) {
    entry<Tracer> {
        TracerScreen(
            japaneseChar = it.japaneseChar,
            onBack = onBack,
            onFinish = onNext
        )
    }
}