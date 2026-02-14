package dev.datlag.kanakoru.feature.level.tracer

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.datlag.kanakoru.model.JapaneseChar
import dev.datlag.kanakoru.ui.LevelScaffold
import dev.datlag.kanakoru.ui.model.CanvasChar
import dev.datlag.kanakoru.ui.model.rememberDollarNCanvasState

@Composable
fun TracerScreen(
    japaneseChar: JapaneseChar,
    onBack: () -> Unit,
    onFinish: () -> Unit
) {
    val canvasChar = remember(japaneseChar) {
        CanvasChar(japaneseChar)
    }
    val state = rememberDollarNCanvasState(
        char = canvasChar
    ) { }

    LevelScaffold(
        title = { Text(text = "Tracer") },
        onBack = onBack,
        canvasState = state,
        templateChar = canvasChar,
        onFinish = onFinish,
        showStart = false,
        showOrder = false
    )
}