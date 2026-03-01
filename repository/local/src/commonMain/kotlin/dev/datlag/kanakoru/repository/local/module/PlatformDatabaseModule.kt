package dev.datlag.kanakoru.repository.local.module

import org.kodein.di.DI

internal expect object PlatformDatabaseModule {

    val di: DI.Module
}