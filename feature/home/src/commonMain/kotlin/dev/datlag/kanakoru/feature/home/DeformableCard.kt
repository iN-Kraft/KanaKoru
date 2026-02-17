package dev.datlag.kanakoru.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.datlag.kanakoru.ui.InclusiveCutoutShape

@Composable
fun DeformableCard(
    onClick: () -> Unit,
    modifier: Modifier,
    circleRadius: Dp,
    gap: Dp = 4.dp,
    cardColors: CardColors,
    circleContent: @Composable BoxScope.() -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    val defaultShape = CardDefaults.shape
    val density = LocalDensity.current
    val (topLeft, bottomLeft, bottomRight) = remember(defaultShape, density) {
        when (defaultShape) {
            is CornerBasedShape -> {
                Triple(
                    with(density) {
                        defaultShape.topStart.toPx(Size.Unspecified, density).toDp()
                    },
                    with(density) {
                        defaultShape.bottomStart.toPx(Size.Unspecified, density).toDp()
                    },
                    with(density) {
                        defaultShape.bottomEnd.toPx(Size.Unspecified, density).toDp()
                    },
                )
            }
            else -> Triple(12.dp, 12.dp, 12.dp)
        }
    }
    val cardShape = InclusiveCutoutShape(
        circleRadius = circleRadius,
        padding = gap,
        topLeft = topLeft,
        bottomLeft = bottomLeft,
        bottomRight = bottomRight,
        smoothing = (topLeft * 2) - (topLeft / 2)
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