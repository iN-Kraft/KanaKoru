package dev.datlag.kanakoru.feature.level.guidedtour

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.datlag.kanakoru.model.JapaneseChar
import dev.datlag.kanakoru.ui.LevelScaffold
import dev.datlag.kanakoru.ui.model.CanvasChar
import dev.datlag.kanakoru.ui.model.rememberDollarNCanvasState
import kotlin.math.max

@Composable
fun GuidedTourScreen(
    japaneseChar: JapaneseChar,
    onBack: () -> Unit,
    onFinish: () -> Unit
) {
    val fullChar = remember(japaneseChar) {
        CanvasChar(japaneseChar)
    }
    val totalStrokes = remember(fullChar) {
        fullChar.strokes.size
    }
    var currentStep by remember { mutableIntStateOf(1) }
    val currentVisibleChar = remember(fullChar, currentStep) {
        fullChar.takeStrokes(currentStep)
    }

    val state = rememberDollarNCanvasState(
        char = currentVisibleChar,
        key = fullChar,
        onResult = { either ->
            either.onRight { result ->
                if (result.score > 0.85F) {
                    if (currentStep < totalStrokes) {
                        currentStep++
                        updateTarget(fullChar.takeStrokes(currentStep))
                    }
                } else {
                    undoLastStroke()
                }
            }
        }
    )

    LevelScaffold(
        title = { Text(text = "Guided Tour $currentStep / $totalStrokes") },
        onUndo = {
            state.undoLastStroke()
            currentStep = max(1, currentStep - 1)
            state.updateTarget(fullChar.takeStrokes(currentStep))
        },
        onClear = {
            state.clear()
            currentStep = 1
            state.updateTarget(fullChar.takeStrokes(currentStep))
        },
        onBack = onBack,
        canvasState = state,
        templateChar = currentVisibleChar,
        onFinish = onFinish,
        showOrder = false
    )
}