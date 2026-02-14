package dev.datlag.kanakoru.feature.level.master

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.datlag.kanakoru.feature.level.master.navigation.Master

fun EntryProviderScope<NavKey>.featureLevelMaster(onBack: () -> Unit, onNext: () -> Unit) {
    entry<Master> {
        MasterScreen(
            japaneseChar = it.japaneseChar,
            onBack = onBack,
            onFinish = onNext
        )
    }
}