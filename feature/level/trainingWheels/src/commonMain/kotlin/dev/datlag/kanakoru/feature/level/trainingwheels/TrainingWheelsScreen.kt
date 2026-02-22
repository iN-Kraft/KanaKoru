package dev.datlag.kanakoru.feature.level.trainingwheels

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.datlag.kanakoru.feature.level.trainingWheels.resources.TrainingWheelsRes
import dev.datlag.kanakoru.feature.level.trainingWheels.resources.level_subtitle
import dev.datlag.kanakoru.feature.level.trainingWheels.resources.level_title
import dev.datlag.kanakoru.model.JapaneseChar
import dev.datlag.kanakoru.ui.LevelScaffold
import dev.datlag.kanakoru.ui.model.CanvasChar
import dev.datlag.kanakoru.ui.model.rememberDollarNCanvasState
import org.jetbrains.compose.resources.stringResource

@Composable
fun TrainingWheelsScreen(
    japaneseChar: JapaneseChar,
    onBack: () -> Unit,
    onFinish: () -> Unit
) {
    val canvasChar = remember(japaneseChar) {
        CanvasChar(japaneseChar)
    }
    val state = rememberDollarNCanvasState(
        char = canvasChar,
        onResult = { }
    )

    LevelScaffold(
        title = { Text(text = stringResource(TrainingWheelsRes.string.level_title)) },
        subtitle = { Text(text = stringResource(TrainingWheelsRes.string.level_subtitle)) },
        onBack = onBack,
        canvasState = state,
        templateChar = canvasChar,
        onFinish = onFinish
    )
}