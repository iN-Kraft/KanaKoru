package dev.datlag.kanakoru

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import arrow.core.Either
import dev.datlag.inkraft.suspendCatching
import dev.datlag.kanakoru.resources.AppRes
import dev.datlag.kanakoru.resources.GoogleSansFlex
import dev.datlag.kanakoru.resources.Inter
import dev.datlag.kanakoru.resources.Inter_Italic
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.FontResource
import org.jetbrains.compose.resources.getFontResourceBytes
import org.jetbrains.compose.resources.rememberResourceEnvironment

object Font {

    private const val Inter = "Inter"
    private const val GoogleSansFlex = "GoogleSansFlex"

    @Composable
    fun inter(): FontFamily? {
        return rememberVariableFontFamily(
            resource = AppRes.font.Inter,
            italicResource = AppRes.font.Inter_Italic,
            identity = Inter
        )
    }

    @Composable
    fun googleSansFlex(): FontFamily? {
        return rememberVariableFontFamily(
            resource = AppRes.font.GoogleSansFlex,
            identity = GoogleSansFlex
        )
    }

    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    @Composable
    fun typography(
        defaultFont: FontFamily,
        emphasizedFont: FontFamily?
    ): Typography {
        val baseTypography = MaterialTheme.typography
        val flexFont = remember(defaultFont, emphasizedFont) {
            emphasizedFont ?: defaultFont
        }

        return remember(defaultFont, emphasizedFont, baseTypography) {
            baseTypography.copy(
                displayLarge = baseTypography.displayLarge.copy(fontFamily = defaultFont),
                displayMedium = baseTypography.displayMedium.copy(fontFamily = defaultFont),
                displaySmall = baseTypography.displaySmall.copy(fontFamily = defaultFont),

                headlineLarge = baseTypography.headlineLarge.copy(fontFamily = defaultFont),
                headlineMedium = baseTypography.headlineMedium.copy(fontFamily = defaultFont),
                headlineSmall = baseTypography.headlineSmall.copy(fontFamily = defaultFont),

                titleLarge = baseTypography.titleLarge.copy(fontFamily = defaultFont),
                titleMedium = baseTypography.titleMedium.copy(fontFamily = defaultFont),
                titleSmall = baseTypography.titleSmall.copy(fontFamily = defaultFont),

                bodyLarge = baseTypography.bodyLarge.copy(fontFamily = defaultFont),
                bodyMedium = baseTypography.bodyMedium.copy(fontFamily = defaultFont),
                bodySmall = baseTypography.bodySmall.copy(fontFamily = defaultFont),

                labelLarge = baseTypography.labelLarge.copy(fontFamily = defaultFont),
                labelMedium = baseTypography.labelMedium.copy(fontFamily = defaultFont),
                labelSmall = baseTypography.labelSmall.copy(fontFamily = defaultFont),

                displayLargeEmphasized = baseTypography.displayLargeEmphasized.copy(fontFamily = flexFont),
                displayMediumEmphasized = baseTypography.displayMediumEmphasized.copy(fontFamily = flexFont),
                displaySmallEmphasized = baseTypography.displaySmallEmphasized.copy(fontFamily = flexFont),

                headlineLargeEmphasized = baseTypography.headlineLargeEmphasized.copy(fontFamily = flexFont),
                headlineMediumEmphasized = baseTypography.headlineMediumEmphasized.copy(fontFamily = flexFont),
                headlineSmallEmphasized = baseTypography.headlineSmallEmphasized.copy(fontFamily = flexFont),

                titleLargeEmphasized = baseTypography.titleLargeEmphasized.copy(fontFamily = flexFont),
                titleMediumEmphasized = baseTypography.titleMediumEmphasized.copy(fontFamily = flexFont),
                titleSmallEmphasized = baseTypography.titleSmallEmphasized.copy(fontFamily = flexFont),

                bodyLargeEmphasized = baseTypography.bodyLargeEmphasized.copy(fontFamily = flexFont),
                bodyMediumEmphasized = baseTypography.bodyMediumEmphasized.copy(fontFamily = flexFont),
                bodySmallEmphasized = baseTypography.bodySmallEmphasized.copy(fontFamily = flexFont),

                labelLargeEmphasized = baseTypography.labelLargeEmphasized.copy(fontFamily = flexFont),
                labelMediumEmphasized = baseTypography.labelMediumEmphasized.copy(fontFamily = flexFont),
                labelSmallEmphasized = baseTypography.labelSmallEmphasized.copy(fontFamily = flexFont)
            )
        }
    }

    @Composable
    fun rememberVariableFontFamily(
        resource: FontResource,
        italicResource: FontResource? = null,
        identity: String = resource.toString()
    ): FontFamily? {
        val efficientResult = rememberPlatformVariableFontFamily(
            resource = resource,
            italicResource = italicResource,
            identity = identity
        )

        return when (efficientResult) {
            is Either.Right -> efficientResult.value
            is Either.Left<Error> -> {
                when (efficientResult.value) {
                    is Error.Initializing -> null
                    is Error.NoData -> rememberFallbackVariableFontFamily(
                        resource = resource,
                        italicResource = italicResource
                    )
                }
            }
        }
    }

    @Composable
    internal fun loadFontBytes(resource: FontResource): State<Either<Error, ByteArray>> {
        val environment = rememberResourceEnvironment()

        return produceState<Either<Error, ByteArray>>(
            initialValue = Either.Left(Error.Initializing),
            key1 = resource,
            key2 = environment
        ) {
            value = suspendCatching {
                getFontResourceBytes(environment, resource)
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
        }
    }

    @Composable
    private fun rememberFallbackVariableFontFamily(
        resource: FontResource,
        italicResource: FontResource?
    ): FontFamily {
        val weights = remember {
            persistentListOf(
                FontWeight.Thin, FontWeight.ExtraLight, FontWeight.Light,
                FontWeight.Normal, FontWeight.Medium, FontWeight.SemiBold,
                FontWeight.Bold, FontWeight.ExtraBold, FontWeight.Black
            )
        }

        val fontList = weights.flatMap { weight ->
            listOfNotNull(
                Font(resource, weight),
                italicResource?.let {
                    Font(it, weight, style = FontStyle.Italic)
                }
            ).toImmutableList()
        }.toImmutableList()

        return remember(fontList) {
            FontFamily(fontList)
        }
    }

    @Serializable
    sealed interface Error {

        @Serializable
        data object Initializing : Error

        @Serializable
        data object NoData : Error
    }
}

@Composable
internal expect fun rememberPlatformVariableFontFamily(
    resource: FontResource,
    italicResource: FontResource? = null,
    identity: String = resource.toString()
): Either<Font.Error, FontFamily>