package dev.datlag.kanakoru.feature.level.finisher

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import dev.datlag.kanakoru.model.JapaneseChar
import dev.datlag.kanakoru.ui.DollarNCanvas
import dev.datlag.kanakoru.ui.LevelScaffold
import dev.datlag.kanakoru.ui.model.CanvasChar
import dev.datlag.kanakoru.ui.model.rememberDollarNCanvasState
import kotlin.math.roundToInt

@Composable
fun FinisherScreen(
    japaneseChar: JapaneseChar,
    completionRatio: Float,
    onBack: () -> Unit,
    onFinish: () -> Unit
) {
    val fullChar = remember(japaneseChar) {
        CanvasChar(japaneseChar)
    }
    val (staticChar, activeChar) = remember(fullChar, completionRatio) {
        val splitIndex = (fullChar.strokes.size * completionRatio).roundToInt()
        fullChar.splitAfterStrokes(splitIndex)
    }
    val state = rememberDollarNCanvasState(
        char = activeChar,
        key = fullChar
    ) { }

    LevelScaffold(
        title = { Text("Finisher") },
        onBack = onBack,
        canvasState = state,
        templateChar = activeChar,
        onFinish = onFinish,
        showOrder = false,
        showStart = false,
        showTemplate = false
    ) { padding ->
        DollarNCanvas(
            char = activeChar,
            state = state,
            modifier = Modifier.fillMaxSize().padding(padding),
            staticStrokes = staticChar.strokes,
            showOrder = false,
            showStart = false,
            showTemplate = false
        )
    }
}