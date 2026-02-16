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
        val efficientResult = efficientVariableFontFamily(WebRes.font.NotoSansJP, NotoSansJP)

        return when (efficientResult) {
            is Either.Left<Error> -> {
                when (efficientResult.value) {
                    is Error.Initializing -> null
                    is Error.NoData -> inefficientVariableFontFamily(WebRes.font.NotoSansJP)
                }
            }
            is Either.Right<FontFamily> -> {
                efficientResult.value
            }
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    private fun inefficientVariableFontFamily(resource: FontResource): FontFamily? {
        val extraLight by preloadFont(resource, weight = FontWeight.ExtraLight)
        val light by preloadFont(resource, weight = FontWeight.Light)
        val thin by preloadFont(resource, weight = FontWeight.Thin)
        val regular by preloadFont(resource, weight = FontWeight.Normal)
        val medium by preloadFont(resource, weight = FontWeight.Medium)
        val semiBold by preloadFont(resource, weight = FontWeight.SemiBold)
        val bold by preloadFont(resource, weight = FontWeight.Bold)
        val extraBold by preloadFont(resource, weight = FontWeight.ExtraBold)
        val black by preloadFont(resource, weight = FontWeight.Black)

        val fontList = remember(extraLight, light, thin, regular, medium, semiBold, bold, extraBold, black) {
            listOfNotNull(extraLight, light, thin, regular, medium, semiBold, bold, extraBold, black).toImmutableList().takeIf { it.size >= 9 }
        }

        return remember(fontList) {
            fontList?.let(::FontFamily)
        }
    }

    @Composable
    private fun efficientVariableFontFamily(resource: FontResource, identity: String): Either<Error, FontFamily> {
        val result = loadFontBytes(resource)
        return remember(result) {
            result.map { bytes ->
                FontFamily(
                    variableFont(identity, bytes, FontWeight.ExtraLight),
                    variableFont(identity, bytes, FontWeight.Light),
                    variableFont(identity, bytes, FontWeight.Thin),
                    variableFont(identity, bytes, FontWeight.Normal),
                    variableFont(identity, bytes, FontWeight.Medium),
                    variableFont(identity, bytes, FontWeight.SemiBold),
                    variableFont(identity, bytes, FontWeight.Bold),
                    variableFont(identity, bytes, FontWeight.ExtraBold),
                    variableFont(identity, bytes, FontWeight.Black)
                )
            }
        }
    }

    @Composable
    private fun loadFontBytes(resource: FontResource): Either<Error, ByteArray> {
        val resourcesEnvironment = rememberResourceEnvironment()
        return produceState<Either<Error, ByteArray>>(
            initialValue = Either.Left(Error.Initializing),
            key1 = resource,
            key2 = resourcesEnvironment
        ) {
            value = suspendCatching {
                getFontResourceBytes(resourcesEnvironment, WebRes.font.NotoSansJP)
            }.fold(
                onSuccess = { bytes ->
                    if (bytes.isNotEmpty()) {
                        Either.Right(bytes)
                    } else {
                        Either.Left(Error.NoData)
                    }
                },
                onFailure = {
                    Either.Left(Error.NoData)
                }
            )
        }.value
    }

    private fun variableFont(
        identity: String,
        data: ByteArray,
        weight: FontWeight,
        style: FontStyle = FontStyle.Normal,
        variationSettings: FontVariation.Settings = FontVariation.Settings(weight, style)
    ) = Font(
        identity = identity,
        data = data,
        weight = weight,
        style = style,
        variationSettings = variationSettings
    )

    @Composable
    fun rememberFallbackFontInitialized(): Boolean {
        val resolver = LocalFontFamilyResolver.current
        val family = notoSansFamily()
        var initialized by remember(family) { mutableStateOf(false) }

        LaunchedEffect(resolver, family) {
            if (family != null) {
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