package dev.datlag.kanakoru.feature.kana

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.datlag.kanakoru.model.JapaneseChar
import dev.datlag.kanakoru.ui.model.CanvasChar
import dev.datlag.kanakoru.ui.model.rememberDollarNCanvasState
import dev.datlag.kanakoru.ui.LevelScaffold

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun KanaDrawScreen(
    char: JapaneseChar,
    onBack: () -> Unit
) {
    val canvasChar = remember(char) { CanvasChar(char) }
    val dollarNCanvasState = rememberDollarNCanvasState(
        char = canvasChar,
        onResult = { eitherResult ->

        }
    )

    LevelScaffold(
        title = { Text(text = "Draw") },
        onBack = onBack,
        canvasState = dollarNCanvasState,
        templateChar = canvasChar,
        onFinish = { }
    )
}