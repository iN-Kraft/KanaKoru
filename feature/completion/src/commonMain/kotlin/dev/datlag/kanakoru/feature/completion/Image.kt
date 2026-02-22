package dev.datlag.kanakoru.feature.completion

import dev.datlag.kanakoru.feature.completion.resources.CompletionRes
import dev.datlag.kanakoru.ui.SVGImage
import kotlinx.collections.immutable.persistentListOf

internal object Image {

    private const val SVG_PATH = "files"

    val celebrationLight by lazy {
        SVGImage.getUri(SVG_PATH / "celebration_light.svg", CompletionRes::getUri)
    }

    val celebrationDark by lazy {
        SVGImage.getUri(SVG_PATH / "celebration_dark.svg", CompletionRes::getUri)
    }

    val winnersLight by lazy {
        SVGImage.getUri(SVG_PATH / "winners_light.svg", CompletionRes::getUri)
    }

    val winnersDark by lazy {
        SVGImage.getUri(SVG_PATH / "winners_dark.svg", CompletionRes::getUri)
    }

    fun random(isDark: Boolean): String {
        val selection = if (isDark) {
            persistentListOf(celebrationDark, winnersDark)
        } else {
            persistentListOf(celebrationLight, winnersLight)
        }

        return selection.random()
    }

    private operator fun String.div(other: String): String {
        return "$this/$other"
    }

}