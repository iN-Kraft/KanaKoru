package dev.datlag.kanakoru.kodein

import android.app.Activity
import android.app.Application
import dev.datlag.inkraft.suspendCatching
import org.kodein.di.DI
import org.kodein.di.DIAware
import kotlin.reflect.safeCast

@Suppress("UNCHECKED_CAST")
inline fun <reified T : Application> Activity.appDI(fallback: (T) -> DI): DI? {
    return suspendCatching {
        this.applicationContext as? DIAware
    }.getOrNull()?.di ?: suspendCatching {
        this.application as? DIAware
    }.getOrNull()?.di ?: suspendCatching {
        DIAware::class.safeCast(this.applicationContext)
    }.getOrNull()?.di ?: suspendCatching {
        DIAware::class.safeCast(this.application)
    }.getOrNull()?.di ?: suspendCatching {
        this.applicationContext as? T
    }.getOrNull()?.let(fallback) ?: suspendCatching {
        this.application as? T
    }.getOrNull()?.let(fallback) ?: suspendCatching {
        T::class.safeCast(this.applicationContext)
    }.getOrNull()?.let(fallback) ?: suspendCatching {
        T::class.safeCast(this.application)
    }.getOrNull()?.let(fallback)
}