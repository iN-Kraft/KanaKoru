package dev.datlag.kanakoru.feature.kana

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import dev.datlag.inkraft.INKraft
import dev.datlag.kanakoru.feature.kana.resources.KanaRes
import dev.datlag.kanakoru.ui.SVGImage

internal object Image {

    private const val SVG_PATH = "files"

    val workInProgress by lazy {
        SVGImage.getUri(SVG_PATH / "undraw_work-in-progress.svg", KanaRes::getUri)
    }

    val aroundTheWorld by lazy {
        SVGImage.getUri(SVG_PATH / "undraw_around-the-world.svg", KanaRes::getUri)
    }

    val workoutLight by lazy {
        SVGImage.getUri(SVG_PATH / "workout_light.svg", KanaRes::getUri)
    }

    val workoutDark by lazy {
        SVGImage.getUri(SVG_PATH / "workout_dark.svg", KanaRes::getUri)
    }

    val hikingLight by lazy {
        SVGImage.getUri(SVG_PATH / "hiking_light.svg", KanaRes::getUri)
    }

    val hikingDark by lazy {
        SVGImage.getUri(SVG_PATH / "hiking_dark.svg", KanaRes::getUri)
    }

    @Composable
    fun workout(dark: Boolean = isSystemInDarkTheme()): String {
        return if (dark) {
            workoutDark
        } else {
            workoutLight
        }
    }

    @Composable
    fun hiking(dark: Boolean = isSystemInDarkTheme()): String {
        return if (dark) {
            hikingDark
        } else {
            hikingLight
        }
    }

    private operator fun String.div(other: String): String {
        return "$this/$other"
    }

}