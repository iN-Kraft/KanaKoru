package dev.datlag.kanakoru.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.materialsymbols.MaterialSymbols
import com.composables.icons.materialsymbols.rounded.Arrow_back_ios_new
import com.composables.icons.materialsymbols.rounded.Check
import com.composables.icons.materialsymbols.rounded.Format_paint
import com.composables.icons.materialsymbols.rounded.Sound_sampler
import com.composables.icons.materialsymbols.rounded.Undo
import dev.datlag.kanakoru.ui.model.CanvasChar
import dev.datlag.kanakoru.ui.model.DollarNCanvasState
import dev.datlag.kommons.locale.Japan
import dev.datlag.kommons.locale.Locale
import kotlinx.collections.immutable.toImmutableList
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
    showStart: Boolean = true,
    showOrder: Boolean = true,
    showTemplate: Boolean = true,
    onUndo: () -> Unit = { canvasState.undoLastStroke() },
    onClear: () -> Unit = { canvasState.clear() },
    content: @Composable () -> Unit = {
        DollarNCanvas(
            char = templateChar,
            state = canvasState,
            modifier = Modifier.fillMaxSize(),
            showStart = showStart,
            showOrder = showOrder,
            showTemplate = showTemplate
        )
    }
) {
    val stateResult by canvasState.lastResult.collectAsState()
    val isSuccess = remember(stateResult) {
        stateResult.isRight { result -> result.score >= 0.85F }
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
                }
            )
        },
        snackbarHost = snackBarHost,
        floatingActionButton = {
            AnimatedVisibility(
                visible = isSuccess,
                enter = scaleIn() + fadeIn(),
                exit = scaleOut() + fadeOut()
            ) {
                FloatingActionButton(
                    onClick = onFinish,
                ) {
                    Icon(
                        imageVector = MaterialSymbols.Rounded.Check,
                        contentDescription = null
                    )
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(padding)
        ) {
            content()

            HorizontalFloatingToolbar(
                expanded = true,
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                val tts = LocalTTS.current
                val japaneseVoices = remember(tts) {
                    tts?.voices?.filter { voice ->
                        val locale = Locale.forLanguageTag(voice.languageTag)
                            ?: Locale(voice.language, voice.region ?: voice.language)

                        locale.country is Japan
                    }.orEmpty().toImmutableList()
                }

                LaunchedEffect(tts, japaneseVoices) {
                    tts?.currentVoice = japaneseVoices.firstOrNull {
                        it.isDefault
                    } ?: japaneseVoices.firstOrNull {
                        !it.isOnline
                    } ?: japaneseVoices.firstOrNull { it.isOnline }
                }

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
                        tts?.enqueue(templateChar.char.toString(), clearQueue = true)
                    },
                    enabled = tts != null,
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
    }
}