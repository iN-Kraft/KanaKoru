package dev.datlag.kanakoru.feature.kana

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.datlag.kanakoru.model.JapaneseChar
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
internal fun getSegmentedShape(
    index: Int,
    totalCount: Int,
    large: CornerBasedShape = MaterialTheme.shapes.largeIncreased,
    small: CornerBasedShape = MaterialTheme.shapes.small
): Shape {
    return if (totalCount == 1) {
        RoundedCornerShape(
            topStart = large.topStart,
            topEnd = large.topEnd,
            bottomStart = large.bottomStart,
            bottomEnd = large.bottomEnd
        )
    } else {
        when (index) {
            0 -> RoundedCornerShape(
                topStart = large.topStart,
                topEnd = large.topEnd,
                bottomStart = small.bottomStart,
                bottomEnd = small.bottomEnd
            )
            totalCount - 1 -> RoundedCornerShape(
                topStart = small.topStart,
                topEnd = small.topEnd,
                bottomStart = large.bottomStart,
                bottomEnd = large.bottomEnd
            )
            else -> small
        }
    }
}

@Composable
internal fun SplitRowContainer(
    chars: ImmutableList<JapaneseChar?>,
    shape: Shape,
    onKana: (JapaneseChar) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth().height(72.dp),
        shape = shape,
        color = MaterialTheme.colorScheme.surfaceContainerHigh
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            chars.forEachIndexed { index, char ->
                if (char != null) {
                    Box(
                        modifier = Modifier
                            .weight(1F)
                            .fillMaxHeight()
                            .clickable { onKana(char) },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = char.value.toString(),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = char.romaji,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                } else {
                    Spacer(modifier = Modifier.weight(1F).fillMaxHeight())
                }

                if (index < chars.lastIndex) {
                    VerticalDivider(
                        modifier = Modifier.width(1.dp).fillMaxHeight(0.5F)
                    )
                }
            }

            if (chars.size < 5) {
                repeat(5 - chars.size) {
                    Spacer(modifier = Modifier.weight(1F))
                }
            }
        }
    }
}