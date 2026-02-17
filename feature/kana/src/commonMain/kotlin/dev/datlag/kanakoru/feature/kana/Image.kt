package dev.datlag.kanakoru.feature.kana

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

    private operator fun String.div(other: String): String {
        return "$this/$other"
    }

}