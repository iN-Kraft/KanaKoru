package dev.datlag.kanakoru.feature.home

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.datlag.kanakoru.feature.home.navigation.Home
import dev.datlag.kanakoru.feature.kana.navigation.Kana
import dev.datlag.kanakoru.ui.NavBackStack

fun EntryProviderScope<NavKey>.featureHome(backStack: NavBackStack<NavKey>) {
    entry<Home> {
        HomeScreen(
            onHiraganaClick = { backStack.push(Kana.Hiragana) },
            onKatakanaClick = { backStack.push(Kana.Katakana) }
        )
    }
}