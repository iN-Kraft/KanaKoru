package dev.datlag.kanakoru.feature.completion

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.datlag.kanakoru.ui.NavBackStack

fun EntryProviderScope<NavKey>.featureCompletion(backStack: NavBackStack<NavKey>) {
    entry<Completion> {
        CompletionScreen(
            japaneseChar = it.japaneseChar,
            onFinish = { backStack.pop() }
        )
    }
}