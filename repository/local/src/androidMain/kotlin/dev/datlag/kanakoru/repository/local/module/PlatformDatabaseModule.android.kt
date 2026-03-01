package dev.datlag.kanakoru.repository.local.module

import android.content.Context
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import dev.datlag.kanakoru.repository.local.KanaKoruDB
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import kotlinx.coroutines.Dispatchers
import org.kodein.di.instance

internal actual object PlatformDatabaseModule {

    private const val NAME = "AndroidDatabaseModule"

    actual val di: DI.Module = DI.Module(NAME) {
        bindSingleton<KanaKoruDB> {
            val driver = AndroidSqliteDriver(
                KanaKoruDB.Schema.synchronous(),
                instance<Context>(),
                DatabaseModule.DB_FILE
            )

            KanaKoruDB(driver)
        }
        bindSingleton(DatabaseModule.REALTIME_DISPATCHER) {
            Dispatchers.IO
        }
    }

}
