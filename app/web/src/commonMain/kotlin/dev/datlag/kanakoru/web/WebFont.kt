package dev.datlag.kanakoru.web

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import arrow.core.Either
import dev.datlag.inkraft.suspendCatching
import dev.datlag.kanakoru.Font
import dev.datlag.kanakoru.web.resources.NotoSansJP
import dev.datlag.kanakoru.web.resources.WebRes
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.FontResource
import org.jetbrains.compose.resources.getFontResourceBytes
import org.jetbrains.compose.resources.preloadFont
import org.jetbrains.compose.resources.rememberResourceEnvironment

internal object WebFont {

    private const val NotoSansJP = "NotoSansJP"

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun notoSansFamily(): FontFamily? {
        return Font.rememberVariableFontFamily(
            resource = WebRes.font.NotoSansJP,
            identity = NotoSansJP
        )
    }

    @Composable
    fun rememberFallbackFontInitialized(): Boolean {
        val resolver = LocalFontFamilyResolver.current
        val family = notoSansFamily()
        var initialized by remember(family) { mutableStateOf(false) }

        if (family != null) {
            LaunchedEffect(resolver, family) {
                resolver.preload(family)
                initialized = true
            }
        }

        return initialized
    }

    @Serializable
    private sealed interface Error {

        @Serializable
        data object Initializing : Error

        @Serializable
        data object NoData : Error
    }

}