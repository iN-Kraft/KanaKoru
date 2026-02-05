package dev.datlag.kanakoru.core

import dev.datlag.kanakoru.ui.module.UIModule
import org.kodein.di.DI

object CoreModule {

    private const val NAME = "CoreModule"
    val di: DI.Module = DI.Module(NAME) {
        import(UIModule.di)
    }

}