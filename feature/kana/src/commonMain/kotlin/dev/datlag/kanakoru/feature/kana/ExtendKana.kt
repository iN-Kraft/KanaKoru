package dev.datlag.kanakoru.feature.kana

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.datlag.kanakoru.feature.kana.navigation.Kana
import dev.datlag.kanakoru.ui.NavBackStack

fun EntryProviderScope<NavKey>.featureKana(backStack: NavBackStack<NavKey>) {
    entry<Kana.Hiragana> { HiraganaScreen() }
    entry<Kana.Katakana> { KatakanaScreen() }
}