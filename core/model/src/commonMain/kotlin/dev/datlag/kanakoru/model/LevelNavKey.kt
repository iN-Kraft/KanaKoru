package dev.datlag.kanakoru.model

import androidx.navigation3.runtime.NavKey

interface LevelNavKey : NavKey {
    val singleStrokeSupport: Boolean
    val multiStrokeSupport: Boolean
}