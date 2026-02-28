package dev.datlag.kanakoru.ui.level

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.composables.icons.materialsymbols.MaterialSymbols
import com.composables.icons.materialsymbols.rounded.Arrow_forward
import dev.datlag.kanakoru.ui.resources.CoreUIRes
import dev.datlag.kanakoru.ui.resources.finish_message_perfect
import dev.datlag.kanakoru.ui.resources.finish_message_success
import dev.datlag.kanakoru.ui.resources.finish_next
import dev.datlag.kanakoru.ui.resources.finish_title_perfect
import dev.datlag.kanakoru.ui.resources.finish_title_success
import kotlinx.collections.immutable.toImmutableList
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
internal fun FinishBottomSheet(
    score: Int,
    onNext: () -> Unit
) {
    val perfectTitles = stringArrayResource(CoreUIRes.array.finish_title_perfect).toImmutableList()
    val successTitles = stringArrayResource(CoreUIRes.array.finish_title_success).toImmutableList()
    val perfectMessages = stringArrayResource(CoreUIRes.array.finish_message_perfect).toImmutableList()
    val successMessages = stringArrayResource(CoreUIRes.array.finish_message_success).toImmutableList()

    val (title, subtitle) = remember(perfectTitles, successTitles, perfectMessages, successMessages) {
        if (score >= 95) {
            perfectTitles.randomOrNull() to perfectMessages.randomOrNull()
        } else {
            successTitles.randomOrNull() to successMessages.randomOrNull()
        }
    }

    if (!title.isNullOrBlank() && !subtitle.isNullOrBlank()) {
        ModalBottomSheet(
            onDismissRequest = { },
            sheetGesturesEnabled = false,
            properties = ModalBottomSheetProperties(
                shouldDismissOnBackPress = false,
                shouldDismissOnClickOutside = false
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                ScoreBadge(score = score)

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = subtitle,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Button(
                    onClick = onNext,
                    modifier = Modifier.fillMaxWidth(),
                    shapes = ButtonDefaults.shapes()
                ) {
                    Text(text = stringResource(CoreUIRes.string.finish_next))
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                    Icon(
                        modifier = Modifier.size(ButtonDefaults.IconSize),
                        imageVector = MaterialSymbols.Rounded.Arrow_forward,
                        contentDescription = null
                    )
                }
            }
        }
    }
}