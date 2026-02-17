package dev.datlag.kanakoru.feature.home

import dev.datlag.kanakoru.feature.home.resources.HomeRes
import dev.datlag.kanakoru.ui.SVGImage

internal object Image {

    private const val SVG_PATH = "files"

    val dreamerLight by lazy {
        SVGImage.getUri(SVG_PATH / "dreamer_light.svg", HomeRes::getUri)
    }

    val dreamerDark by lazy {
        SVGImage.getUri(SVG_PATH / "dreamer_dark.svg", HomeRes::getUri)
    }

    val relaxingAtHomeLight by lazy {
        SVGImage.getUri(SVG_PATH / "relaxing_at_home_light.svg", HomeRes::getUri)
    }

    val relaxingAtHomeDark by lazy {
        SVGImage.getUri(SVG_PATH / "relaxing_at_home_dark.svg", HomeRes::getUri)
    }

    private operator fun String.div(other: String): String {
        return "$this/$other"
    }

}