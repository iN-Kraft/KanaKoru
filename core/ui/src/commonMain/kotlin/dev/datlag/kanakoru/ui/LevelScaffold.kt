package dev.datlag.kanakoru.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.composables.icons.materialsymbols.MaterialSymbols
import com.composables.icons.materialsymbols.rounded.Arrow_back_ios_new
import com.composables.icons.materialsymbols.rounded.Check
import com.composables.icons.materialsymbols.rounded.Format_paint
import dev.datlag.kanakoru.ui.model.CanvasChar
import dev.datlag.kanakoru.ui.model.DollarNCanvasState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
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
    onClear: () -> Unit = { canvasState.clear() },
    content: @Composable (PaddingValues) -> Unit = { padding ->
        DollarNCanvas(
            char = templateChar,
            state = canvasState,
            modifier = Modifier.fillMaxSize().padding(padding),
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
                },
                actions = {
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
        },
        content = content
    )
}