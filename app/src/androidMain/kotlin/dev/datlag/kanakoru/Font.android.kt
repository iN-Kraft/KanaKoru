package dev.datlag.kanakoru

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import arrow.core.Either
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.FontResource

@Composable
internal actual fun rememberPlatformVariableFontFamily(
    resource: FontResource,
    italicResource: FontResource?,
    identity: String
): Either<Font.Error, FontFamily> {
    val defaultFont = Font(resource)
    val italicFont = italicResource?.let { Font(it, style = FontStyle.Italic) }

    return remember(defaultFont, italicFont) {
        Either.Right(FontFamily(listOfNotNull(defaultFont, italicFont)))
    }
}