package dev.datlag.kanakoru.repository

import app.cash.sqldelight.db.SqlDriver
import dev.datlag.inkraft.suspendCatching
import dev.datlag.kanakoru.model.JapaneseChar
import dev.datlag.kanakoru.repository.local.KanaKoruDB
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.coroutines.CoroutineContext
import dev.datlag.kanakoru.repository.local.DrawingRepository as LocalDBRepo

class DrawingRepository(
    local: LocalDBRepo?,
    val localSqlDriver: SqlDriver?,
    val localRealtimeCoroutineContext: CoroutineContext
) {

    private var localRepo: LocalDBRepo? = local
    private val localInitMutex = Mutex()

    val recommendedHiragana: Flow<JapaneseChar?> = flow {
        val repo = getOrInitLocalRepo()
        if (repo != null) {
            emitAll(repo.recommendedHiragana)
        } else {
            emit(null)
        }
    }

    val recommendedKatakana: Flow<JapaneseChar?> = flow {
        val repo = getOrInitLocalRepo()
        if (repo != null) {
            emitAll(repo.recommendedKatakana)
        } else {
            emit(null)
        }
    }

    private suspend fun getOrInitLocalRepo(): LocalDBRepo? {
        if (localRepo != null) return localRepo

        return localInitMutex.withLock {
            if (localRepo != null) {
                return@withLock localRepo
            }

            createLocalRepo()
        }
    }

    private suspend fun createLocalDB(): KanaKoruDB? {
        val driver = localSqlDriver ?: return null
        val createdSchema = suspendCatching {
            KanaKoruDB.Schema.create(driver).await()
        }.isSuccess

        return if (createdSchema) {
            suspendCatching { KanaKoruDB(driver) }.getOrNull()
        } else {
            null
        }
    }

    private suspend fun createLocalRepo(): LocalDBRepo? {
        val db = createLocalDB() ?: return null
        return LocalDBRepo(
            localDataSource = db,
            realtimeCoroutineContext = localRealtimeCoroutineContext
        ).also { localRepo = it }
    }
}