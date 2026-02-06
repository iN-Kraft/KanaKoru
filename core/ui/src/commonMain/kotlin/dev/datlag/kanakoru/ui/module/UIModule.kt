package dev.datlag.kanakoru.ui.module

import coil3.ImageLoader
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.svg.SvgDecoder
import dev.datlag.kanakoru.ui.ColoredSVGDecoder
import io.ktor.client.HttpClient
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import org.kodein.di.instanceOrNull

object UIModule {

    private const val NAME = "UIModule"
    internal const val SVG_IMAGE_LOADER = "SVGImageLoader"

    val di: DI.Module = DI.Module(NAME) {
        bindSingleton<ImageLoader>(tag = SVG_IMAGE_LOADER) {
            val httpClient = instanceOrNull<HttpClient>()

            ImageLoader.Builder(context = instance())
                .components {
                    add(ColoredSVGDecoder.Factory())
                    add(SvgDecoder.Factory())
                    if (httpClient != null) {
                        add(KtorNetworkFetcherFactory(httpClient = httpClient))
                    }
                }
                .build()
        }
    }

}