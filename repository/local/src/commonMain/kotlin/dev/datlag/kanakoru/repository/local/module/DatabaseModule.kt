package dev.datlag.kanakoru.repository.local.module

import dev.datlag.kanakoru.kodein.optionalInstance
import dev.datlag.kanakoru.kodein.optionalSingleton
import dev.datlag.kanakoru.repository.local.DrawingRepository
import dev.datlag.kanakoru.repository.local.KanaKoruDB
import kotlinx.coroutines.Dispatchers
import org.kodein.di.DI
import kotlin.coroutines.CoroutineContext

object DatabaseModule {

    private const val NAME = "DatabaseModule"
    const val REALTIME_DISPATCHER = "RealTimeDispatcher"
    internal const val DB_FILE = "kanakoru.db"

    val di: DI.Module = DI.Module(NAME) {
        import(PlatformDatabaseModule.di)

        optionalSingleton<DrawingRepository> {
            val driver = optionalInstance<KanaKoruDB>() ?: return@optionalSingleton null
            val realtimeDispatcher = optionalInstance<CoroutineContext>(REALTIME_DISPATCHER) ?: Dispatchers.Default

            DrawingRepository(
                localDataSource = driver,
                realtimeCoroutineContext = realtimeDispatcher
            )
        }
    }
}