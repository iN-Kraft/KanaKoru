package dev.datlag.kanakoru.repository.local.module

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.worker.createDefaultWebWorkerDriver
import dev.datlag.inkraft.suspendCatching
import dev.datlag.kanakoru.kodein.optionalSingleton
import org.kodein.di.DI

internal actual object PlatformDatabaseModule {

    private const val NAME = "WebDatabaseModule"

    actual val di: DI.Module = DI.Module(NAME) {
        optionalSingleton<SqlDriver> {
            suspendCatching { createDefaultWebWorkerDriver() }.getOrNull()
        }
    }
}