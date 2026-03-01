package dev.datlag.kanakoru.repository.local.module

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import dev.datlag.kanakoru.repository.local.KanaKoruDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.kodein.di.DI
import org.kodein.di.bindSingleton

internal actual object PlatformDatabaseModule {

    private const val NAME = "NativeDatabaseModule"

    actual val di: DI.Module = DI.Module(NAME) {
        bindSingleton<KanaKoruDB> {
            val driver = NativeSqliteDriver(
                KanaKoruDB.Schema.synchronous(),
                DatabaseModule.DB_FILE
            )

            KanaKoruDB(driver)
        }
        bindSingleton(DatabaseModule.REALTIME_DISPATCHER) {
            Dispatchers.IO
        }
    }
}