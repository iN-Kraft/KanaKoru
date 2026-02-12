package dev.datlag.kanakoru.ui.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Path
import arrow.core.Either
import arrow.core.raise.either
import dev.datlag.kanakoru.dollarn.Point
import dev.datlag.kanakoru.dollarn.DollarN
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableMap

@Stable
class DollarNCanvasState(
    private val char: CanvasChar,
    private val onResult: (Either<DollarN.Error, DollarN.Result>) -> Unit
) {

    private val recognizer = DollarN(
        mapOf(
            char.char to char.points
        ).toImmutableMap()
    )

    private val _completedPoints = mutableStateListOf<ImmutableList<Point>>()
    val completedPoints: ImmutableList<ImmutableList<Point>>
        get() = _completedPoints.toImmutableList()

    private val _currentPoints = mutableStateListOf<Point>()
    val currentPoints: ImmutableList<Point>
        get() = _currentPoints.toImmutableList()

    val completedPaths = mutableStateListOf<Path>()
    val currentPath = Path()

    var currentPathVersion by mutableLongStateOf(0)
        private set

    fun onDragStart(startPoint: Point) {
        _currentPoints.clear()
        _currentPoints.add(startPoint)

        mutatePath {
            reset()
            moveTo(startPoint.x, startPoint.y)
        }
    }

    fun onDrag(point: Point) {
        _currentPoints.add(point)

        mutatePath {
            lineTo(point.x, point.y)
        }
    }

    fun onDragEnd() {
        if (_currentPoints.isNotEmpty()) {
            val finishedPoints = _currentPoints.toImmutableList()
            _completedPoints.add(finishedPoints)

            val cachedPath = Path()
            cachedPath.addPath(currentPath)
            completedPaths.add(cachedPath)

            _currentPoints.clear()
            mutatePath {
                reset()
            }

            val result = either {
                recognizer.recognize(completedPoints)
            }

            onResult(result)
        }
    }

    fun onDragCancel() {
        _currentPoints.clear()
        mutatePath {
            reset()
        }
    }

    fun clear() {
        _completedPoints.clear()
        _currentPoints.clear()

        completedPaths.clear()
        mutatePath {
            reset()
        }
    }

    private fun mutatePath(block: Path.() -> Unit) {
        block(currentPath)
        currentPathVersion++
    }
}

@Composable
fun rememberDollarNCanvasState(
    char: CanvasChar,
    onResult: (Either<DollarN.Error, DollarN.Result>) -> Unit
): DollarNCanvasState {
    val currentOnResult by rememberUpdatedState(onResult)

    return remember(char) {
        DollarNCanvasState(
            char = char,
            onResult = { result -> currentOnResult(result) }
        )
    }
}