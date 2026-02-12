package dev.datlag.kanakoru.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.input.pointer.pointerInput
import dev.datlag.kanakoru.dollarn.Point
import dev.datlag.kanakoru.ui.model.CanvasChar
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import dev.datlag.kanakoru.ui.model.DollarNCanvasState

@Composable
fun DollarNCanvas(
    char: CanvasChar,
    state: DollarNCanvasState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val contentColor = MaterialTheme.colorScheme.onBackground
    val drawingColor = MaterialTheme.colorScheme.tertiary
    val templateColor = MaterialTheme.colorScheme.surfaceContainer
    val badgeColor = MaterialTheme.colorScheme.primary
    val onBadgeColor = MaterialTheme.colorScheme.onPrimary
    val textMeasurer = rememberTextMeasurer()
    val strokeStyle = remember {
        Stroke(
            width = 20F,
            cap = StrokeCap.Round,
            join = StrokeJoin.Round
        )
    }
    val badgeSize = remember(strokeStyle) {
        (strokeStyle.width / 2F).dp
    }

    BoxWithConstraints(modifier = modifier) {
        val scale = remember(constraints.maxWidth, constraints.maxHeight, char) {
            val minDim = minOf(constraints.maxWidth, constraints.maxHeight).toFloat()
            val maxDim = maxOf(char.originalWidth, char.originalHeight).coerceAtLeast(1F)
            minDim / maxDim
        }
        val offsetX = remember(constraints.maxWidth, char.originalWidth, scale) {
            (constraints.maxWidth - (char.originalWidth * scale)) / 2
        }
        val offsetY = remember(constraints.maxHeight, char.originalHeight, scale) {
            (constraints.maxHeight - (char.originalHeight * scale)) / 2
        }

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(enabled) {
                    if (enabled) {
                        detectDragGestures(
                            onDragStart = { offset ->
                                state.onDragStart(Point(offset.x, offset.y))
                            },
                            onDrag = { change, _ ->
                                change.consume()
                                state.onDrag(Point(change.position.x, change.position.y))
                            },
                            onDragEnd = {
                                state.onDragEnd()
                            },
                            onDragCancel = {
                                state.onDragCancel()
                            }
                        )
                    }
                }
        ) {
            withTransform({
                translate(left = offsetX, top = offsetY)
                scale(scale = scale, pivot = Offset.Zero)
            }) {
                char.strokes.forEach { stroke ->
                    drawPath(
                        path = stroke.path,
                        color = templateColor,
                        style = Stroke(
                            width = strokeStyle.width / scale,
                            cap = strokeStyle.cap,
                            join = strokeStyle.join
                        )
                    )
                }
            }

            state.completedPaths.forEach { path ->
                drawPath(
                    path = path,
                    color = contentColor,
                    style = strokeStyle
                )
            }

            if (state.currentPathVersion > 0) {
                drawPath(
                    path = state.currentPath,
                    color = drawingColor,
                    style = strokeStyle
                )
            }

            char.strokes.forEach { stroke ->
                val screenX = (stroke.startOffset.x * scale) + offsetX
                val screenY = (stroke.startOffset.y * scale) + offsetY
                val badgeCenter = Offset(screenX, screenY)

                drawCircle(
                    color = badgeColor,
                    radius = badgeSize.toPx(),
                    center = badgeCenter
                )

                val result = textMeasurer.measure((stroke.index + 1).toString())
                drawText(
                    textLayoutResult = result,
                    topLeft = Offset(
                        x = screenX - (result.size.width / 2),
                        y = screenY - (result.size.height / 2)
                    ),
                    color = onBadgeColor
                )
            }
        }
    }
}