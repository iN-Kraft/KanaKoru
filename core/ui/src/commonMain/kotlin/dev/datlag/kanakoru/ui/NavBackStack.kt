package dev.datlag.kanakoru.ui

import androidx.compose.runtime.snapshots.Snapshot
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.StateObject
import androidx.navigation3.runtime.NavBackStack as NavBS
import androidx.navigation3.runtime.NavKey
import kotlin.reflect.KClass

class NavBackStack<T : NavKey>(
    private val base: NavBS<T> = NavBS()
) : StateObject by base, MutableList<T> by base, RandomAccess by base {

    constructor(base: SnapshotStateList<T>) : this(NavBS(base))
    constructor(vararg elements: T) : this(NavBS(elements = elements))

    fun push(key: T): Boolean = add(element = key)

    fun replaceCurrent(key: T) = mutate {
        base[lastIndex] = key
    }

    fun replaceAll(vararg keys: T): Boolean {
        if (keys.isEmpty()) {
            return false
        }

        return mutate {
            clear()
            addAll(keys)
        }
    }

    fun pop(): Boolean {
        if (size <= 1) {
            return false
        }

        return mutate {
            removeLastOrNull()
        } != null
    }

    fun popWhile(predicate: (T) -> Boolean) = mutate {
        while (size > 1 && predicate(last())) {
            removeLastOrNull()
        }
    }

    fun popTo(index: Int, inclusive: Boolean = false) = mutate {
        if (index !in 0..<size) {
            return@mutate
        }

        val removeFrom = if (inclusive) index else index + 1

        when {
            removeFrom >= size -> return@mutate
            removeFrom <= 0 -> replaceAll(first())
            else -> removeRange(removeFrom, size)
        }
    }

    fun popTo(type: KClass<out NavKey>, inclusive: Boolean = false) {
        val index = indexOfLast { it::class == type }
        popTo(index, inclusive)
    }

    fun popTo(key: T, inclusive: Boolean = false) {
        popTo(type = key::class, inclusive)
    }

    fun popToFirst() = popTo(0)

    private fun removeRange(from: Int, to: Int) = mutate {
        subList(from, to).clear()
    }

    private fun <R> mutate(block: () -> R) = Snapshot.withMutableSnapshot(block)
}