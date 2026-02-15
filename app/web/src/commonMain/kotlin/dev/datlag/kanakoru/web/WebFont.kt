package dev.datlag.kanakoru.web

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalFontFamilyResolver
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
import org.jetbrains.compose.resources.preloadFont

internal object WebFont {

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun notoSansFamily(): FontFamily? {
        val extraLight by preloadFont(WebRes.font.NotoSansJP_ExtraLight, weight = FontWeight.ExtraLight)
        val light by preloadFont(WebRes.font.NotoSansJP_Light, weight = FontWeight.Light)
        val thin by preloadFont(WebRes.font.NotoSansJP_Thin, weight = FontWeight.Thin)
        val regular by preloadFont(WebRes.font.NotoSansJP_Regular, weight = FontWeight.Normal)
        val medium by preloadFont(WebRes.font.NotoSansJP_Medium, weight = FontWeight.Medium)
        val semiBold by preloadFont(WebRes.font.NotoSansJP_SemiBold, weight = FontWeight.SemiBold)
        val bold by preloadFont(WebRes.font.NotoSansJP_Bold, weight = FontWeight.Bold)
        val extraBold by preloadFont(WebRes.font.NotoSansJP_ExtraBold, weight = FontWeight.ExtraBold)
        val black by preloadFont(WebRes.font.NotoSansJP_Black, weight = FontWeight.Black)

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

}