package dev.datlag.kanakoru

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import arrow.core.Either
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import org.jetbrains.compose.resources.FontResource

@Composable
internal actual fun rememberPlatformVariableFontFamily(
    resource: FontResource,
    italicResource: FontResource?,
    identity: String
): Either<Font.Error, FontFamily> {
    fun createFont(
        data: ByteArray,
        weight: FontWeight,
        style: FontStyle = FontStyle.Normal,
        variationSettings: FontVariation.Settings = FontVariation.Settings(weight = weight, style = style)
    ) = Font(
        identity = buildString {
            append(identity)
            if (style == FontStyle.Italic) {
                append("_i")
            } else {
                append("_r")
            }
            append(weight.weight)
        },
        data = data,
        weight = weight,
        style = style,
        variationSettings = variationSettings
    )

    val fontByteResult by Font.loadFontBytes(resource)
    val italicFontByteResult = italicResource?.let { Font.loadFontBytes(it) }
    val italicFontBytes = italicFontByteResult?.value?.getOrNull()
    val weights = remember {
        persistentListOf(
            FontWeight.Normal,
            FontWeight.Thin, FontWeight.ExtraLight, FontWeight.Light,
            FontWeight.Medium, FontWeight.SemiBold,
            FontWeight.Bold, FontWeight.ExtraBold, FontWeight.Black
        )
    }

    val fontList = remember(fontByteResult, italicFontBytes) {
        fontByteResult.map { bytes ->
            weights.flatMap { weight ->
                listOfNotNull(
                    createFont(bytes, weight),
                    italicFontBytes?.let { createFont(it, weight, FontStyle.Italic) }
                )
            }.toImmutableList()
        }
    }

    return remember(fontList) {
        fontList.map { list ->
            FontFamily(list)
        }
    }
}