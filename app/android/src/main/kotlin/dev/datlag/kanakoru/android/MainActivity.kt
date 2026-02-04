package dev.datlag.kanakoru.android

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.view.WindowCompat
import dev.datlag.kanakoru.Root
import dev.datlag.kanakoru.kodein.appDI
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.compose.LocalDI

class MainActivity : ComponentActivity(), DIAware {

    override val di: DI by lazy {
        requireNotNull(appDI<App> { it.di })
    }

    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            CompositionLocalProvider(
                LocalDI provides di
            ) {
                MaterialExpressiveTheme(
                    colorScheme = systemColorScheme()
                ) {
                    Root()
                }
            }
        }
    }

    @Composable
    private fun systemColorScheme(): ColorScheme {
        return if (isSystemInDarkTheme()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                dynamicDarkColorScheme(this)
            } else {
                darkColorScheme()
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                dynamicLightColorScheme(this)
            } else {
                lightColorScheme()
            }
        }
    }
}