package dev.datlag.kanakoru.feature.level.trainingwheels

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.datlag.kanakoru.feature.level.trainingwheels.navigation.TrainingWheels

fun EntryProviderScope<NavKey>.featureLevelTrainingWheels(onBack: () -> Unit, onNext: () -> Unit) {
    entry<TrainingWheels> {
        TrainingWheelsScreen(
            japaneseChar = it.japaneseChar,
            onBack = onBack,
            onFinish = onNext
        )
    }
}