package dev.datlag.kanakoru

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
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
import org.jetbrains.compose.resources.Font

internal object Font {

    @Composable
    fun notoSansFamily(): FontFamily {
        return FontFamily(
            Font(WebRes.font.NotoSansJP_ExtraLight, weight = FontWeight.ExtraLight),
            Font(WebRes.font.NotoSansJP_Light, weight = FontWeight.Light),
            Font(WebRes.font.NotoSansJP_Thin, weight = FontWeight.Thin),
            Font(WebRes.font.NotoSansJP_Regular, weight = FontWeight.Normal),
            Font(WebRes.font.NotoSansJP_Medium, weight = FontWeight.Medium),
            Font(WebRes.font.NotoSansJP_SemiBold, weight = FontWeight.SemiBold),
            Font(WebRes.font.NotoSansJP_Bold, weight = FontWeight.Bold),
            Font(WebRes.font.NotoSansJP_ExtraBold, weight = FontWeight.ExtraBold),
            Font(WebRes.font.NotoSansJP_Black, weight = FontWeight.Black),
        )
    }

    @Composable
    fun notoSansTypography(): Typography {
        val notoSans = notoSansFamily()

        return with(MaterialTheme.typography) {
            copy(
                displayLarge = displayLarge.copy(fontFamily = notoSans),
                displayMedium = displayMedium.copy(fontFamily = notoSans),
                displaySmall = displaySmall.copy(fontFamily = notoSans),
                headlineLarge = headlineLarge.copy(fontFamily = notoSans),
                headlineMedium = headlineMedium.copy(fontFamily = notoSans),
                headlineSmall = headlineSmall.copy(fontFamily = notoSans),
                titleLarge = titleLarge.copy(fontFamily = notoSans),
                titleMedium = titleMedium.copy(fontFamily = notoSans),
                titleSmall = titleSmall.copy(fontFamily = notoSans),
                bodyLarge = bodyLarge.copy(fontFamily = notoSans),
                bodyMedium = bodyMedium.copy(fontFamily = notoSans),
                bodySmall = bodySmall.copy(fontFamily = notoSans),
                labelLarge = labelLarge.copy(fontFamily = notoSans),
                labelMedium = labelMedium.copy(fontFamily = notoSans),
                labelSmall = labelSmall.copy(fontFamily = notoSans),
            )
        }
    }

}