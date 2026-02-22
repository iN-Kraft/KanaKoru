package dev.datlag.kanakoru.ui.level

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.datlag.kanakoru.ui.resources.CoreUIRes
import dev.datlag.kanakoru.ui.resources.finish_accuracy
import dev.datlag.kanakoru.ui.resources.finish_percentage
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
internal fun ScoreBadge(
    score: Int,
    modifier: Modifier = Modifier
) {
    var visibleScore by remember { mutableIntStateOf(0) }
    val animatedProgress by animateFloatAsState(
        targetValue = visibleScore / 100F,
        animationSpec = MaterialTheme.motionScheme.slowEffectsSpec()
    )
    val animatedScoreText by animateIntAsState(
        targetValue = visibleScore,
        animationSpec = MaterialTheme.motionScheme.slowEffectsSpec()
    )

    LaunchedEffect(score) {
        delay(300)
        visibleScore = score
    }

    Box(
        modifier = modifier.size(140.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularWavyProgressIndicator(
            progress = { animatedProgress },
            modifier = Modifier.matchParentSize(),
            color = if (score >= 100) {
                MaterialTheme.colorScheme.tertiary
            } else {
                MaterialTheme.colorScheme.primary
            },
            stroke = Stroke(
                width = with(LocalDensity.current) { 10.dp.toPx() },
                cap = StrokeCap.Round
            ),
            wavelength = 20.dp
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(CoreUIRes.string.finish_percentage, animatedScoreText),
                style = MaterialTheme.typography.displaySmallEmphasized,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = stringResource(CoreUIRes.string.finish_accuracy),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}