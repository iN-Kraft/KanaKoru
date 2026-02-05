package dev.datlag.kanakoru.dollarn

import kotlinx.serialization.Serializable

@Serializable
internal data class Box(
    val x: Float,
    val y: Float,
    val width: Float,
    val height: Float
)
