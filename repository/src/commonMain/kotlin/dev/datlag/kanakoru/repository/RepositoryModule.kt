package dev.datlag.kanakoru.repository

import dev.datlag.kanakoru.kodein.optionalInstance
import dev.datlag.kanakoru.kodein.optionalSingleton
import dev.datlag.kanakoru.repository.local.module.DatabaseModule
import dev.datlag.kanakoru.repository.local.DrawingRepository as LocalDBRepo
import org.kodein.di.DI

object RepositoryModule {

    private const val NAME = "RepositoryModule"

    val di: DI.Module = DI.Module(NAME) {
        import(DatabaseModule.di)

        optionalSingleton<DrawingRepository> {
            val local = optionalInstance<LocalDBRepo>() ?: return@optionalSingleton null

            DrawingRepository(local = local)
        }
    }

}