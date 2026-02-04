package dev.datlag.kanakoru.ui

import kotlin.math.roundToInt

internal object Constants {
    const val COLOR_MIN_VALUE = 0
    const val COLOR_MAX_VALUE = 255
    const val COLOR_HEX_LENGTH = 9

    fun colorFloatToInt(value: Float): Int = (value * COLOR_MAX_VALUE.toFloat()).roundToInt().coerceIn(COLOR_MIN_VALUE, COLOR_MAX_VALUE)

}