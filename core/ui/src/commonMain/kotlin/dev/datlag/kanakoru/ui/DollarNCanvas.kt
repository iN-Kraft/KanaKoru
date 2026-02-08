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
import dev.datlag.kanakoru.ui.model.DollarNCanvasState

@Composable
fun DollarNCanvas(
    char: CanvasChar,
    state: DollarNCanvasState,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = modifier) {
        val scale = remember(constraints.maxWidth, constraints.maxHeight, char) {
            val minDim = minOf(constraints.maxWidth, constraints.maxHeight)
            minDim / maxOf(char.originalWidth, char.originalHeight)
        }
        val offsetX = (constraints.maxWidth - (char.originalWidth * scale)) / 2
        val offsetY = (constraints.maxHeight - (char.originalHeight * scale)) / 2
        val contentColor = MaterialTheme.colorScheme.onBackground
        val drawingColor = MaterialTheme.colorScheme.tertiary
        val templateColor = MaterialTheme.colorScheme.surfaceContainer
        val strokeStyle = Stroke(
            width = 20F,
            cap = StrokeCap.Round,
            join = StrokeJoin.Round
        )

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
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

            state.completedStrokes.forEach { stroke ->
                if (stroke.size > 1) {
                    val path = Path().apply {
                        moveTo(stroke.first().x, stroke.first().y)
                        for (i in 1 until stroke.size) {
                            lineTo(stroke[i].x, stroke[i].y)
                        }
                    }

                    drawPath(
                        path = path,
                        color = contentColor,
                        style = strokeStyle
                    )
                }
            }

            if (state.currentStroke.size > 1) {
                val path = Path().apply {
                    moveTo(state.currentStroke.first().x, state.currentStroke.first().y)

                    for (i in 1 until state.currentStroke.size) {
                        lineTo(state.currentStroke[i].x, state.currentStroke[i].y)
                    }
                }

                drawPath(
                    path = path,
                    color = drawingColor,
                    style = strokeStyle
                )
            }
        }
    }
}