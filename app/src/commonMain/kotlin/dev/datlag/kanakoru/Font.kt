package dev.datlag.kanakoru

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import dev.datlag.kanakoru.resources.AppRes
import dev.datlag.kanakoru.resources.GoogleSansFlex
import dev.datlag.kanakoru.resources.Inter
import dev.datlag.kanakoru.resources.Inter_Italic
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.Font

object Font {

    @Composable
    fun inter(): FontFamily {
        val extraLight = Font(resource = AppRes.font.Inter, weight = FontWeight.ExtraLight)
        val extraLightItalic = Font(resource = AppRes.font.Inter_Italic, weight = FontWeight.ExtraLight, style = FontStyle.Italic)
        val light = Font(resource = AppRes.font.Inter, weight = FontWeight.Light)
        val lightItalic = Font(resource = AppRes.font.Inter_Italic, weight = FontWeight.Light, style = FontStyle.Italic)
        val thin = Font(resource = AppRes.font.Inter, weight = FontWeight.Thin)
        val thinItalic = Font(resource = AppRes.font.Inter_Italic, weight = FontWeight.Thin, style = FontStyle.Italic)
        val normal = Font(resource = AppRes.font.Inter, weight = FontWeight.Normal)
        val normalItalic = Font(resource = AppRes.font.Inter_Italic, weight = FontWeight.Normal, style = FontStyle.Italic)
        val medium = Font(resource = AppRes.font.Inter, weight = FontWeight.Medium)
        val mediumItalic = Font(resource = AppRes.font.Inter_Italic, weight = FontWeight.Medium, style = FontStyle.Italic)
        val semiBold = Font(resource = AppRes.font.Inter, weight = FontWeight.SemiBold)
        val semiBoldItalic = Font(resource = AppRes.font.Inter_Italic, weight = FontWeight.SemiBold, style = FontStyle.Italic)
        val bold = Font(resource = AppRes.font.Inter, weight = FontWeight.Bold)
        val boldItalic = Font(resource = AppRes.font.Inter_Italic, weight = FontWeight.Bold, style = FontStyle.Italic)
        val extraBold = Font(resource = AppRes.font.Inter, weight = FontWeight.ExtraBold)
        val extraBoldItalic = Font(resource = AppRes.font.Inter_Italic, weight = FontWeight.ExtraBold, style = FontStyle.Italic)
        val black = Font(resource = AppRes.font.Inter, weight = FontWeight.Black)
        val blackItalic = Font(resource = AppRes.font.Inter_Italic, weight = FontWeight.Black, style = FontStyle.Italic)

        val fontList = remember(
            extraLight, extraLightItalic,
            light, lightItalic,
            thin, thinItalic,
            normal, normalItalic,
            medium, mediumItalic,
            semiBold, semiBoldItalic,
            bold, boldItalic,
            extraBold, extraBoldItalic,
            black, blackItalic
        ) {
            persistentListOf(
                extraLight, extraLightItalic,
                light, lightItalic,
                thin, thinItalic,
                normal, normalItalic,
                medium, mediumItalic,
                semiBold, semiBoldItalic,
                bold, boldItalic,
                extraBold, extraBoldItalic,
                black, blackItalic
            )
        }

        return remember(fontList) {
            FontFamily(fontList)
        }
    }

    @Composable
    fun googleSansFlex(): FontFamily {
        val extraLight = Font(resource = AppRes.font.GoogleSansFlex, weight = FontWeight.ExtraLight)
        val light = Font(resource = AppRes.font.GoogleSansFlex, weight = FontWeight.Light)
        val thin = Font(resource = AppRes.font.GoogleSansFlex, weight = FontWeight.Thin)
        val normal = Font(resource = AppRes.font.GoogleSansFlex, weight = FontWeight.Normal)
        val medium = Font(resource = AppRes.font.GoogleSansFlex, weight = FontWeight.Medium)
        val semiBold = Font(resource = AppRes.font.GoogleSansFlex, weight = FontWeight.SemiBold)
        val bold = Font(resource = AppRes.font.GoogleSansFlex, weight = FontWeight.Bold)
        val extraBold = Font(resource = AppRes.font.GoogleSansFlex, weight = FontWeight.ExtraBold)
        val black = Font(resource = AppRes.font.GoogleSansFlex, weight = FontWeight.Black)

        val fontList = remember(
            extraLight,
            light,
            thin,
            normal,
            medium,
            semiBold,
            bold,
            extraBold,
            black
        ) {
            persistentListOf(
                extraLight,
                light,
                thin,
                normal,
                medium,
                semiBold,
                bold,
                extraBold,
                black
            )
        }

        return remember(fontList) {
            FontFamily(fontList)
        }
    }

    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    @Composable
    fun typography(): Typography {
        val interFont = inter()
        val flexFont = googleSansFlex()
        val baseTypography = MaterialTheme.typography

        return remember(interFont, flexFont, baseTypography) {
            baseTypography.copy(
                displayLarge = baseTypography.displayLarge.copy(fontFamily = interFont),
                displayMedium = baseTypography.displayMedium.copy(fontFamily = interFont),
                displaySmall = baseTypography.displaySmall.copy(fontFamily = interFont),

                headlineLarge = baseTypography.headlineLarge.copy(fontFamily = interFont),
                headlineMedium = baseTypography.headlineMedium.copy(fontFamily = interFont),
                headlineSmall = baseTypography.headlineSmall.copy(fontFamily = interFont),

                titleLarge = baseTypography.titleLarge.copy(fontFamily = interFont),
                titleMedium = baseTypography.titleMedium.copy(fontFamily = interFont),
                titleSmall = baseTypography.titleSmall.copy(fontFamily = interFont),

                bodyLarge = baseTypography.bodyLarge.copy(fontFamily = interFont),
                bodyMedium = baseTypography.bodyMedium.copy(fontFamily = interFont),
                bodySmall = baseTypography.bodySmall.copy(fontFamily = interFont),

                labelLarge = baseTypography.labelLarge.copy(fontFamily = interFont),
                labelMedium = baseTypography.labelMedium.copy(fontFamily = interFont),
                labelSmall = baseTypography.labelSmall.copy(fontFamily = interFont),

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
}