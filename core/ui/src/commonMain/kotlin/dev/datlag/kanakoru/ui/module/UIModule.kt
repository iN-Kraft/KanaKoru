package dev.datlag.kanakoru.ui.module

import coil3.ImageLoader
import coil3.svg.SvgDecoder
import dev.datlag.kanakoru.ui.ColoredSVGDecoder
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

object UIModule {

    private const val NAME = "UIModule"
    internal const val SVG_IMAGE_LOADER = "SVGImageLoader"

    val di: DI.Module = DI.Module(NAME) {
        bindSingleton<ImageLoader>(tag = SVG_IMAGE_LOADER) {
            ImageLoader.Builder(context = instance())
                .components {
                    add(ColoredSVGDecoder.Factory)
                    add(SvgDecoder.Factory())
                }
                .build()
        }
    }

}