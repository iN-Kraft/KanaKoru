package dev.datlag.kanakoru.feature.level

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.datlag.kanakoru.feature.level.navigation.Level
import dev.datlag.kanakoru.ui.NavBackStack

fun EntryProviderScope<NavKey>.featureLevel(backStack: NavBackStack<NavKey>) {
    entry<Level> { LevelScreen(it.char) }
}