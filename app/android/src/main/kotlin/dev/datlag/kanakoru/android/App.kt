package dev.datlag.kanakoru.android

import android.app.Application
import dev.datlag.inkraft.suspendCatching
import dev.datlag.kanakoru.core.CoreModule
import io.sentry.kotlin.multiplatform.Context
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.bindSingleton

class App : Application(), DIAware {

    private val appContext: Context
        get() = suspendCatching {
            applicationContext
        }.getOrNull() ?: suspendCatching {
            baseContext
        }.getOrNull() ?: this

    override val di: DI = DI {
        import(CoreModule.di)

        bindSingleton<Context> {
            appContext
        }
    }
}