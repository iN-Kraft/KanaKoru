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