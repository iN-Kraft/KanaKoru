package dev.datlag.kanakoru.feature.kana

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.input.pointer.pointerInput
import com.composables.icons.materialsymbols.MaterialSymbols
import com.composables.icons.materialsymbols.rounded.Arrow_back_ios_new
import com.composables.icons.materialsymbols.rounded.Format_paint
import dev.datlag.kanakoru.feature.kana.navigation.Kana

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun KanaDrawScreen(
    char: Kana.Char,
    onBack: () -> Unit
) {
    val lines = remember { mutableStateListOf<List<Offset>>() }
    val currentLine = remember { mutableStateListOf<Offset>() }
    val defaultPaths = remember(char) {
        char.pathData.map { path ->
            PathParser().parsePathString(path).toPath()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        shapes = IconButtonDefaults.shapes()
                    ) {
                        Icon(
                            imageVector = MaterialSymbols.Rounded.Arrow_back_ios_new,
                            contentDescription = null
                        )
                    }
                },
                title = {
                    Text("Draw")
                },
                actions = {
                    IconButton(
                        onClick = {
                            lines.clear()
                            currentLine.clear()
                        },
                        shapes = IconButtonDefaults.shapes()
                    ) {
                        Icon(
                            imageVector = MaterialSymbols.Rounded.Format_paint,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        val contentColor = MaterialTheme.colorScheme.onBackground
        val drawingColor = MaterialTheme.colorScheme.tertiary
        val templateColor = MaterialTheme.colorScheme.surfaceContainer

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { startOffset ->
                            currentLine.clear()
                            currentLine.add(startOffset)
                        },
                        onDrag = { change, _ ->
                            change.consume()
                            currentLine.add(change.position)
                        },
                        onDragEnd = {
                            if (currentLine.isNotEmpty()) {
                                lines.add(currentLine.toList())
                                currentLine.clear()
                            }
                        }
                    )
                }
        ) {
            val originalSize = 109f
            val scaleFactor = size.minDimension / originalSize
            val offsetX = (size.width - (originalSize * scaleFactor)) / 2
            val offsetY = (size.height - (originalSize * scaleFactor)) / 2
            val strokeStyle = Stroke(
                width = 20F,
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )

            withTransform({
                translate(left = offsetX, top = offsetY)
                scale(scale = scaleFactor, pivot = Offset.Zero)
            }) {
                defaultPaths.forEach { path ->
                    drawPath(
                        path = path,
                        color = templateColor,
                        style = Stroke(width = 20F / scaleFactor, cap = StrokeCap.Round)
                    )
                }
            }

            lines.forEach { line ->
                if (line.size > 1) {
                    drawPath(
                        path = Path().apply {
                            moveTo(line.first().x, line.first().y)

                            for (i in 1 until line.size) {
                                lineTo(line[i].x, line[i].y)
                            }
                        },
                        color = contentColor,
                        style = strokeStyle
                    )
                }
            }

            if (currentLine.size > 1) {
                drawPath(
                    path = Path().apply {
                        moveTo(currentLine.first().x, currentLine.first().y)

                        for (i in 1 until currentLine.size) {
                            lineTo(currentLine[i].x, currentLine[i].y)
                        }
                    },
                    color = drawingColor,
                    style = strokeStyle
                )
            }
        }
    }
}