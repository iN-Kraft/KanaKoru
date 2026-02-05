package dev.datlag.kanakoru.feature.kana

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import dev.datlag.inkraft.suspendCatching
import dev.datlag.kanakoru.feature.kana.resources.KanaRes
import dev.datlag.kanakoru.feature.kana.resources.allDrawableResources
import org.jetbrains.compose.resources.DrawableResource

internal object Image {

    val workInProgress by lazy {
        KanaRes.getUri("files/undraw_work-in-progress.svg")
    }

    val aroundTheWorld by lazy {
        KanaRes.getUri("files/undraw_around-the-world.svg")
    }


    val MaterialSymbolsArrow_back_ios: ImageVector
        get() {
            if (_MaterialSymbolsArrow_back_ios != null) return _MaterialSymbolsArrow_back_ios!!

            _MaterialSymbolsArrow_back_ios = ImageVector.Builder(
                name = "arrow_back_ios",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 960f,
                viewportHeight = 960f
            ).apply {
                path(
                    fill = SolidColor(Color.Black)
                ) {
                    moveTo(142f, 480f)
                    lineToRelative(294f, 294f)
                    quadToRelative(15f, 15f, 14.5f, 35f)
                    reflectiveQuadTo(435f, 844f)
                    quadToRelative(-15f, 15f, -35f, 15f)
                    reflectiveQuadToRelative(-35f, -15f)
                    lineTo(57f, 537f)
                    quadToRelative(-12f, -12f, -18f, -27f)
                    reflectiveQuadToRelative(-6f, -30f)
                    quadToRelative(0f, -15f, 6f, -30f)
                    reflectiveQuadToRelative(18f, -27f)
                    lineToRelative(308f, -308f)
                    quadToRelative(15f, -15f, 35.5f, -14.5f)
                    reflectiveQuadTo(436f, 116f)
                    quadToRelative(15f, 15f, 15f, 35f)
                    reflectiveQuadToRelative(-15f, 35f)
                    lineTo(142f, 480f)
                    close()
                }
            }.build()

            return _MaterialSymbolsArrow_back_ios!!
        }

    private var _MaterialSymbolsArrow_back_ios: ImageVector? = null



    private val drawableReverseMap by lazy {
        KanaRes.allDrawableResources.entries.associate {
            it.value to it.key
        }
    }

    private fun drawableUri(res: DrawableResource): String? {
        val key = drawableReverseMap[res] ?: return null
        val extension = listOf("svg")

        return extension.firstNotNullOfOrNull { ext ->
            suspendCatching {
                KanaRes.getUri("files/${key}.$ext")
            }.getOrNull()
        }
    }

}