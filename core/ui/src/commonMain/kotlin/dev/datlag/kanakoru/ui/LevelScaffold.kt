package dev.datlag.kanakoru.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.FloatingToolbarHorizontalFabPosition
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.WavyProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import arrow.core.getOrElse
import com.composables.icons.materialsymbols.MaterialSymbols
import com.composables.icons.materialsymbols.rounded.Arrow_back_ios_new
import com.composables.icons.materialsymbols.rounded.Check
import com.composables.icons.materialsymbols.rounded.Format_paint
import com.composables.icons.materialsymbols.rounded.Grid_3x3
import com.composables.icons.materialsymbols.rounded.Grid_3x3_off
import com.composables.icons.materialsymbols.rounded.Sound_sampler
import com.composables.icons.materialsymbols.rounded.Undo
import dev.datlag.kanakoru.ui.canvas.DollarNCanvasConfig
import dev.datlag.kanakoru.ui.canvas.rememberDollarNCanvasConfig
import dev.datlag.kanakoru.ui.level.FinishBottomSheet
import dev.datlag.kanakoru.ui.model.CanvasChar
import dev.datlag.kanakoru.ui.model.DollarNCanvasState
import dev.datlag.kommons.locale.Japan
import dev.datlag.kommons.locale.Locale
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import nl.marc_apps.tts.experimental.ExperimentalVoiceApi
import nl.marc_apps.tts.rememberTextToSpeechOrNull
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalVoiceApi::class
)
@Composable
fun LevelScaffold(
    title: @Composable () -> Unit,
    onBack: () -> Unit,
    canvasState: DollarNCanvasState,
    templateChar: CanvasChar,
    onFinish: () -> Unit,
    modifier: Modifier = Modifier,
    subtitle: @Composable () -> Unit = { },
    snackBarHost: @Composable () -> Unit = { },
    config: DollarNCanvasConfig = rememberDollarNCanvasConfig(),
    onUndo: () -> Unit = { canvasState.undoLastStroke() },
    onClear: () -> Unit = { canvasState.clear() },
    content: @Composable () -> Unit = {
        DollarNCanvas(
            char = templateChar,
            state = canvasState,
            modifier = Modifier.fillMaxSize(),
            config = config
        )
    }
) {
    val stateResult by canvasState.lastResult.collectAsState()
    val isSuccess = remember(stateResult) {
        stateResult.isRight { result -> result.isSuccess }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                subtitle = subtitle,
                title = title,
                titleHorizontalAlignment = Alignment.CenterHorizontally,
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
                actions = {
                    IconButton(
                        onClick = {
                            config.toggleGrid()
                        },
                        shapes = IconButtonDefaults.shapes()
                    ) {
                        Icon(
                            imageVector = if (config.showGrid) {
                                MaterialSymbols.Rounded.Grid_3x3_off
                            } else {
                                MaterialSymbols.Rounded.Grid_3x3
                            },
                            contentDescription = null
                        )
                    }
                }
            )
        },
        snackbarHost = snackBarHost
    ) { padding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(padding)
        ) {
            content()

            HorizontalFloatingToolbar(
                expanded = true,
                modifier = Modifier.align(Alignment.BottomCenter).padding(FloatingToolbarDefaults.ScreenOffset)
            ) {
                val tts = LocalTTS.current

                IconButton(
                    onClick = onUndo,
                    shapes = IconButtonDefaults.shapes()
                ) {
                    Icon(
                        imageVector = MaterialSymbols.Rounded.Undo,
                        contentDescription = null
                    )
                }
                FilledIconButton(
                    onClick = {
                        tts.enqueue(templateChar.char.toString())
                    },
                    enabled = tts.isAvailable,
                    shapes = IconButtonDefaults.shapes()
                ) {
                    Icon(
                        imageVector = MaterialSymbols.Rounded.Sound_sampler,
                        contentDescription = null
                    )
                }
                IconButton(
                    onClick = onClear,
                    shapes = IconButtonDefaults.shapes()
                ) {
                    Icon(
                        imageVector = MaterialSymbols.Rounded.Format_paint,
                        contentDescription = null
                    )
                }
            }
        }

        var sheetVisible by remember(isSuccess) { mutableStateOf(isSuccess) }

        if (sheetVisible) {
            FinishBottomSheet(
                score = stateResult.getOrNull()?.displayScore ?: 100,
                onNext = {
                    sheetVisible = false
                    onFinish()
                }
            )
        }
    }
}