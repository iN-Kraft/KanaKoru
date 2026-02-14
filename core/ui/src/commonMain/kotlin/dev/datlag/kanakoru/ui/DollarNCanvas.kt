package dev.datlag.kanakoru.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.datlag.kanakoru.dollarn.DollarN
import dev.datlag.kanakoru.dollarn.Point
import dev.datlag.kanakoru.ui.model.CanvasChar
import dev.datlag.kanakoru.ui.model.DollarNCanvasState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun DollarNCanvas(
    char: CanvasChar,
    state: DollarNCanvasState,
    modifier: Modifier = Modifier,
    showStart: Boolean = true,
    showOrder: Boolean = true,
    showTemplate: Boolean = true,
    staticStrokes: ImmutableList<CanvasChar.Stroke> = persistentListOf(),
    baseStrokeWidth: Dp = 18.dp
) {
    val density = LocalDensity.current
    val contentColor = MaterialTheme.colorScheme.onBackground
    val drawingColor = MaterialTheme.colorScheme.tertiary
    val templateColor = MaterialTheme.colorScheme.surfaceContainer
    val badgeColor = MaterialTheme.colorScheme.primary
    val onBadgeColor = MaterialTheme.colorScheme.onPrimary

    val strokeWidthPx = remember(baseStrokeWidth, density) {
        with(density) { baseStrokeWidth.toPx() }
    }
    val badgeRadius = remember(strokeWidthPx) {
        strokeWidthPx / 2F
    }
    val textSizeSp = remember(baseStrokeWidth, density) {
        with(density) { (baseStrokeWidth * 0.8F).toSp() }
    }
    val textMeasurer = rememberTextMeasurer()
    val strokeStyle = remember(strokeWidthPx) {
        Stroke(
            width = strokeWidthPx,
            cap = StrokeCap.Round,
            join = StrokeJoin.Round
        )
    }
    val stateResult by state.lastResult.collectAsState()
    val drawingEnabled = remember(stateResult) {
        stateResult.isLeft { error ->
            when (error) {
                is DollarN.Error.NoMatch -> true
                is DollarN.Error.StrokeCountMismatch -> {
                    error.actual < error.expected
                }
                else -> false
            }
        }
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
        val templateStrokeStyle = remember(scale, strokeWidthPx) {
            Stroke(
                width = strokeWidthPx / scale,
                cap = strokeStyle.cap,
                join = strokeStyle.join
            )
        }

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(drawingEnabled) {
                    if (drawingEnabled) {
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
            if (showTemplate) {
                withTransform({
                    translate(left = offsetX, top = offsetY)
                    scale(scale = scale, pivot = Offset.Zero)
                }) {
                    char.strokes.forEach { stroke ->
                        drawPath(
                            path = stroke.path,
                            color = templateColor,
                            style = templateStrokeStyle
                        )
                    }
                }
            }

            if (staticStrokes.isNotEmpty()) {
                withTransform({
                    translate(left = offsetX, top = offsetY)
                    scale(scale = scale, pivot = Offset.Zero)
                }) {
                    staticStrokes.forEach { stroke ->
                        drawPath(
                            path = stroke.path,
                            color = contentColor,
                            style = templateStrokeStyle
                        )
                    }
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

            if (showStart || showOrder) {
                val textStyle = TextStyle(
                    fontSize = textSizeSp,
                    color = onBadgeColor
                )

                char.strokes.forEach { stroke ->
                    val screenX = (stroke.startOffset.x * scale) + offsetX
                    val screenY = (stroke.startOffset.y * scale) + offsetY

                    if (showStart) {
                        val badgeCenter = Offset(screenX, screenY)

                        drawCircle(
                            color = badgeColor,
                            radius = badgeRadius,
                            center = badgeCenter
                        )
                    }

                    if (showOrder) {
                        val result = textMeasurer.measure(
                            text = (stroke.index + 1).toString(),
                            style = textStyle
                        )
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
    }
}