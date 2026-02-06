package dev.datlag.kanakoru

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import dev.datlag.kanakoru.web.resources.NotoSansJP_Black
import dev.datlag.kanakoru.web.resources.NotoSansJP_Bold
import dev.datlag.kanakoru.web.resources.NotoSansJP_ExtraBold
import dev.datlag.kanakoru.web.resources.NotoSansJP_ExtraLight
import dev.datlag.kanakoru.web.resources.NotoSansJP_Light
import dev.datlag.kanakoru.web.resources.NotoSansJP_Medium
import dev.datlag.kanakoru.web.resources.NotoSansJP_Regular
import dev.datlag.kanakoru.web.resources.NotoSansJP_SemiBold
import dev.datlag.kanakoru.web.resources.NotoSansJP_Thin
import dev.datlag.kanakoru.web.resources.WebRes
import kotlinx.collections.immutable.toImmutableList
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.FontResource

internal object Font {

    @Composable
    fun notoSansFamily(): FontFamily? {
        val extraLight = loadFont(WebRes.font.NotoSansJP_ExtraLight, weight = FontWeight.ExtraLight)
        val light = loadFont(WebRes.font.NotoSansJP_Light, weight = FontWeight.Light)
        val thin = loadFont(WebRes.font.NotoSansJP_Thin, weight = FontWeight.Thin)
        val regular = loadFont(WebRes.font.NotoSansJP_Regular, weight = FontWeight.Normal)
        val medium = loadFont(WebRes.font.NotoSansJP_Medium, weight = FontWeight.Medium)
        val semiBold = loadFont(WebRes.font.NotoSansJP_SemiBold, weight = FontWeight.SemiBold)
        val bold = loadFont(WebRes.font.NotoSansJP_Bold, weight = FontWeight.Bold)
        val extraBold = loadFont(WebRes.font.NotoSansJP_ExtraBold, weight = FontWeight.ExtraBold)
        val black = loadFont(WebRes.font.NotoSansJP_Black, weight = FontWeight.Black)

        val fontList = remember(extraLight, light, thin, regular, medium, semiBold, bold, extraBold, black) {
            listOfNotNull(extraLight, light, thin, regular, medium, semiBold, bold, extraBold, black).toImmutableList()
        }

        return remember(fontList) {
            if (fontList.isEmpty()) {
                null
            } else {
                FontFamily(fontList)
            }
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    private fun loadFont(
        resource: FontResource,
        weight: FontWeight,
        style: FontStyle = FontStyle.Normal,
        variationSettings: FontVariation.Settings = FontVariation.Settings(weight, style)
    ): Font? {
        val resState = remember(resource, weight, variationSettings) { mutableStateOf<Font?>(null) }.apply {
            value = Font(resource, weight, style, variationSettings)
        }

        return resState.value
    }

    @Composable
    fun typography(fontFamily: FontFamily): Typography {
        val baseTypography = MaterialTheme.typography

        return remember(fontFamily, baseTypography) {
            baseTypography.copy(
                displayLarge = baseTypography.displayLarge.copy(fontFamily = fontFamily),
                displayMedium = baseTypography.displayMedium.copy(fontFamily = fontFamily),
                displaySmall = baseTypography.displaySmall.copy(fontFamily = fontFamily),
                headlineLarge = baseTypography.headlineLarge.copy(fontFamily = fontFamily),
                headlineMedium = baseTypography.headlineMedium.copy(fontFamily = fontFamily),
                headlineSmall = baseTypography.headlineSmall.copy(fontFamily = fontFamily),
                titleLarge = baseTypography.titleLarge.copy(fontFamily = fontFamily),
                titleMedium = baseTypography.titleMedium.copy(fontFamily = fontFamily),
                titleSmall = baseTypography.titleSmall.copy(fontFamily = fontFamily),
                bodyLarge = baseTypography.bodyLarge.copy(fontFamily = fontFamily),
                bodyMedium = baseTypography.bodyMedium.copy(fontFamily = fontFamily),
                bodySmall = baseTypography.bodySmall.copy(fontFamily = fontFamily),
                labelLarge = baseTypography.labelLarge.copy(fontFamily = fontFamily),
                labelMedium = baseTypography.labelMedium.copy(fontFamily = fontFamily),
                labelSmall = baseTypography.labelSmall.copy(fontFamily = fontFamily),
            )
        }
    }

}