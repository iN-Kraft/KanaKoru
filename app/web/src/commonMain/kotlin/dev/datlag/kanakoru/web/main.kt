package dev.datlag.kanakoru.web

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeViewport
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.annotation.DelicateCoilApi
import coil3.memory.MemoryCache
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.request.crossfade
import coil3.svg.SvgDecoder
import dev.datlag.kanakoru.Root
import dev.datlag.kanakoru.core.CoreModule
import io.ktor.client.HttpClient
import io.ktor.client.engine.js.Js
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.compose.LocalDI
import org.kodein.di.instance
import org.kodein.di.instanceOrNull

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3ExpressiveApi::class,
    DelicateCoilApi::class
)
fun main() {
    val di = DI {
        import(CoreModule.di)

        bindSingleton<PlatformContext> {
            PlatformContext.INSTANCE
        }
        bindSingleton<HttpClient> {
            HttpClient(Js)
        }
        bindSingleton<ImageLoader> {
            val httpClient = instanceOrNull<HttpClient>()

            ImageLoader.Builder(instance<PlatformContext>())
                .components {
                    add(SvgDecoder.Factory())
                    if (httpClient != null) {
                        add(KtorNetworkFetcherFactory(httpClient = httpClient))
                    }
                }
                .memoryCache {
                    MemoryCache.Builder()
                        .maxSizePercent(instance<PlatformContext>())
                        .build()
                }
                .crossfade(true)
                .build()
        }
    }
    val imageLoader by di.instanceOrNull<ImageLoader>()
    imageLoader?.let(SingletonImageLoader::setUnsafe)

    ComposeViewport {
        val fontsInitialized = WebFont.rememberFallbackFontInitialized()

        CompositionLocalProvider(
            LocalDI provides di
        ) {
            MaterialExpressiveTheme(
                colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    if (fontsInitialized) {
                        Root()
                    } else {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "Loading Font..."
                        )
                    }
                }
            }
        }
    }
}