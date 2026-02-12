package dev.datlag.kanakoru.ui.common

import dev.datlag.kanakoru.dollarn.DollarN
import dev.datlag.kanakoru.ui.model.CanvasChar
import kotlinx.collections.immutable.persistentMapOf

operator fun DollarN.Companion.invoke(vararg char: CanvasChar): DollarN {
    return DollarN(
        templates = persistentMapOf(*char.map { it.char to it.points }.toTypedArray())
    )
}