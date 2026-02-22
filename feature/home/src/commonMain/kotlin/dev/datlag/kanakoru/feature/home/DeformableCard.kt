package dev.datlag.kanakoru.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import dev.datlag.kanakoru.ui.InclusiveCutoutShape

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun DeformableCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    circleRadius: Dp,
    gap: Dp = 4.dp,
    cardColors: CardColors,
    defaultShape: CornerBasedShape = MaterialTheme.shapes.largeIncreased,
    circleContent: @Composable BoxScope.() -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    val density = LocalDensity.current
    val direction = LocalLayoutDirection.current
    val (topLeft, bottomLeft, bottomRight) = remember(defaultShape, density, direction) {
        val (left, right) = if (direction == LayoutDirection.Ltr) {
            defaultShape.bottomStart to defaultShape.bottomEnd
        } else {
            defaultShape.bottomEnd to defaultShape.bottomStart
        }

        Triple(
            with(density) {
                defaultShape.topStart.toPx(Size.Unspecified, density).toDp()
            },
            with(density) {
                left.toPx(Size.Unspecified, density).toDp()
            },
            with(density) {
                right.toPx(Size.Unspecified, density).toDp()
            },
        )
    }
    val cardShape = InclusiveCutoutShape(
        circleRadius = circleRadius,
        padding = gap,
        topLeft = topLeft,
        bottomLeft = bottomLeft,
        bottomRight = bottomRight,
        smoothing = topLeft
    )

    Box(modifier = modifier) {
        Card(
            onClick = onClick,
            shape = cardShape,
            colors = cardColors,
            modifier = Modifier.matchParentSize()
        ) {
            content()
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(circleRadius * 2F),
            contentAlignment = Alignment.Center
        ) {
            circleContent()
        }
    }
}