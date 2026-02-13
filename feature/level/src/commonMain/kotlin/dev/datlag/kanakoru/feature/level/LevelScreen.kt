package dev.datlag.kanakoru.feature.level

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import dev.datlag.kanakoru.feature.level.guidedtour.featureLevelGuidedTour
import dev.datlag.kanakoru.feature.level.trainingwheels.featureLevelTrainingWheels
import dev.datlag.kanakoru.model.JapaneseChar
import dev.datlag.kanakoru.ui.common.rememberNavBackStack

@Composable
fun LevelScreen(
    char: JapaneseChar,
    onBack: () -> Unit,
    viewModel: LevelViewModel = viewModel { LevelViewModel(char) }
) {
    val backStack = rememberNavBackStack(LevelSerialization.configuration, viewModel.startDestination)
    val nextLevel = remember(viewModel, backStack) {
        {
            val next = viewModel.getNextRoute(backStack.last())

            if (next != null) {
                backStack.push(next)
            }
        }
    }

    NavDisplay(
        modifier = Modifier.fillMaxSize(),
        backStack = backStack,
        entryProvider = entryProvider {
            featureLevelGuidedTour(onBack = onBack, onNext = nextLevel)
            featureLevelTrainingWheels(onBack = onBack, onNext = nextLevel)
        },
        onBack = onBack
    )
}