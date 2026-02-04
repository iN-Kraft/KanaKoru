package dev.datlag.kanakoru.kodein

import arrow.core.Option
import org.kodein.di.DI
import org.kodein.di.DirectDI
import org.kodein.di.bindSingleton
import org.kodein.di.bindings.NoArgBindingDI
import org.kodein.di.direct
import org.kodein.di.instanceOrNull

inline fun <reified T : Any> DI.Builder.optionalSingleton(
    tag: Any? = null,
    noinline initializer: NoArgBindingDI<Any>.() -> T?,
) {
    bindSingleton<Option<T>>(tag = tag) { Option.fromNullable(initializer()) }
}

inline fun <reified T : Any> DirectDI.optionalInstance(tag: Any? = null): T? =
    instanceOrNull<Option<T>>(tag = tag)?.getOrNull()

inline fun <reified T : Any> DI.optionalInstance(tag: Any? = null): T? =
    direct.optionalInstance(tag = tag)
