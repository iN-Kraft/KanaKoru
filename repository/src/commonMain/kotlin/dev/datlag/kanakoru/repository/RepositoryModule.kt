package dev.datlag.kanakoru.repository

import app.cash.sqldelight.db.SqlDriver
import dev.datlag.kanakoru.kodein.optionalInstance
import dev.datlag.kanakoru.kodein.optionalSingleton
import dev.datlag.kanakoru.repository.local.module.DatabaseModule
import kotlinx.coroutines.Dispatchers
import dev.datlag.kanakoru.repository.local.DrawingRepository as LocalDBRepo
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import kotlin.coroutines.CoroutineContext

object RepositoryModule {

    private const val NAME = "RepositoryModule"

    val di: DI.Module = DI.Module(NAME) {
        import(DatabaseModule.di)

        bindSingleton<DrawingRepository> {
            val local = optionalInstance<LocalDBRepo>()
            val localSqlDriver = optionalInstance<SqlDriver>()
            val localRealtimeDispatcher = optionalInstance<CoroutineContext>(DatabaseModule.REALTIME_DISPATCHER)

            DrawingRepository(
                local = local,
                localSqlDriver = localSqlDriver,
                localRealtimeCoroutineContext = localRealtimeDispatcher ?: Dispatchers.Default
            )
        }
    }

}