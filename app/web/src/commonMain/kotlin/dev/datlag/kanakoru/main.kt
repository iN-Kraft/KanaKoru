package dev.datlag.kanakoru

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import coil3.PlatformContext
import dev.datlag.kanakoru.core.CoreModule
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.compose.LocalDI

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3ExpressiveApi::class)
fun main() {
    val di = DI {
        import(CoreModule.di)

        bindSingleton<PlatformContext> {
            PlatformContext.INSTANCE
        }
    }

    ComposeViewport {
        CompositionLocalProvider(
            LocalDI provides di
        ) {
            MaterialExpressiveTheme(
                colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
            ) {
                Root()
            }
        }
    }
}