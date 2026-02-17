package dev.datlag.kanakoru.ui

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

class InclusiveCutoutShape(
    private val topLeft: Dp = 12.dp,
    private val bottomLeft: Dp = 12.dp,
    private val bottomRight: Dp = 12.dp,
    private val circleRadius: Dp,
    private val padding: Dp = 10.dp,
    private val smoothing: Dp = 20.dp,
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline = Outline.Generic(
        path = Path().apply {
            val w = size.width
            val h = size.height

            val tl = with(density) { topLeft.toPx() }
            val bl = with(density) { bottomLeft.toPx() }
            val br = with(density) { bottomRight.toPx() }

            val cRadius = with(density) { circleRadius.toPx() }
            val pad = with(density) { padding.toPx() }
            val s = with(density) { smoothing.toPx() }

            val cutoutSize = (cRadius * 2) + pad

            // Top-Left Corner
            moveTo(0f, tl)
            if (tl > 0) {
                quadraticTo(0f, 0f, tl, 0f)
            } else {
                lineTo(0f, 0f)
            }

            val startCutoutX = w - cutoutSize - s
            lineTo(x = startCutoutX, y = 0f)

            // Cut
            quadraticTo(
                x1 = w - cutoutSize,
                y1 = 0f,
                x2 = w - cutoutSize,
                y2 = s,
            )
            quadraticTo(
                x1 = w - cutoutSize,
                y1 = cutoutSize,
                x2 = w - s,
                y2 = cutoutSize,
            )
            quadraticTo(
                x1 = w,
                y1 = cutoutSize,
                x2 = w,
                y2 = cutoutSize + s,
            )

            // Bottom-Right Corner
            lineTo(x = w, y = h - br)
            if (br > 0) {
                quadraticTo(x1 = w, y1 = h, x2 = w - br, y2 = h)
            } else {
                lineTo(x = w, y = h)
            }

            // Bottom-Left Corner
            lineTo(x = bl, y = h)
            if (bl > 0) {
                quadraticTo(x1 = 0f, y1 = h, x2 = 0f, y2 = h - bl)
            } else {
                lineTo(x = 0f, y = h)
            }

            close()
        },
    )
}