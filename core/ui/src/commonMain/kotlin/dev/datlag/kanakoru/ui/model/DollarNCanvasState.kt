package dev.datlag.kanakoru.ui.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
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

    private val _completedStrokes = mutableStateListOf<List<Point>>()
    val completedStrokes: ImmutableList<ImmutableList<Point>>
        get() = _completedStrokes.map { it.toImmutableList() }.toImmutableList()

    private val _currentStroke = mutableStateListOf<Point>()
    val currentStroke: ImmutableList<Point>
        get() = _currentStroke.toImmutableList()

    fun onDragStart(startPoint: Point) {
        _currentStroke.clear()
        _currentStroke.add(startPoint)
    }

    fun onDrag(point: Point) {
        _currentStroke.add(point)
    }

    fun onDragEnd() {
        if (_currentStroke.isNotEmpty()) {
            val finishedStroke = _currentStroke.toList()
            _completedStrokes.add(finishedStroke)
            _currentStroke.clear()

            val result = either {
                recognizer.recognize(_completedStrokes)
            }

            onResult(result)
        }
    }

    fun onDragCancel() {
        _currentStroke.clear()
    }

    fun clear() {
        _completedStrokes.clear()
        _currentStroke.clear()
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