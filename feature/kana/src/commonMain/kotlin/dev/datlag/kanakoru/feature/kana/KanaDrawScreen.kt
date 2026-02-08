package dev.datlag.kanakoru.feature.kana

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import arrow.core.Either
import com.composables.icons.materialsymbols.MaterialSymbols
import com.composables.icons.materialsymbols.rounded.Arrow_back_ios_new
import com.composables.icons.materialsymbols.rounded.Check
import com.composables.icons.materialsymbols.rounded.Format_paint
import dev.datlag.kanakoru.model.JapaneseChar
import dev.datlag.kanakoru.ui.DollarNCanvas
import dev.datlag.kanakoru.ui.model.CanvasChar
import dev.datlag.kanakoru.ui.model.rememberDollarNCanvasState
import dev.datlag.kanakoru.dollarn.DollarN
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun KanaDrawScreen(
    char: JapaneseChar,
    onBack: () -> Unit
) {
    val canvasChar = remember(char) { CanvasChar(char) }
    var recognitionState by remember { mutableStateOf<Either<DollarN.Error, DollarN.Result>>(Either.Left(DollarN.Error.NoStrokes)) }
    val dollarNCanvasState = rememberDollarNCanvasState(
        char = canvasChar,
        onResult = { eitherResult ->
            recognitionState = eitherResult
        }
    )

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
                            dollarNCanvasState.clear()
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
        },
        bottomBar = {
            recognitionState.onLeft {
                Text(text = "State: $it")
            }.onRight {
                Text(text = "Match: ${(it.score * 100F).roundToInt()}%")
            }
        },
        floatingActionButton = {
            when (val current = recognitionState) {
                is Either.Left -> { }
                is Either.Right<DollarN.Result> -> {
                    if (current.value.score >= 0.9) {
                        FloatingActionButton(
                            onClick = { },
                        ) {
                            Icon(
                                imageVector = MaterialSymbols.Rounded.Check,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        DollarNCanvas(
            char = canvasChar,
            state = dollarNCanvasState,
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        )
    }
}