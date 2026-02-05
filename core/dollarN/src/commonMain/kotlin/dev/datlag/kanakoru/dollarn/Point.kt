package dev.datlag.kanakoru.dollarn

import kotlinx.serialization.Serializable
import kotlin.math.sqrt

@Serializable
data class Point(
    val x: Float,
    val y: Float
) {

    fun distanceTo(other: Point): Float {
        val dx = this.x - other.x
        val dy = this.y - other.y
        return sqrt(dx * dx + dy * dy)
    }

}
