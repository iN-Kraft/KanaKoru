package dev.datlag.kanakoru

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import kotlinx.serialization.Serializable

object Color {

    val lightScheme = lightColorScheme(
        primary = Scheme.Light.primary,
        onPrimary = Scheme.Light.onPrimary,
        primaryContainer = Scheme.Light.primaryContainer,
        onPrimaryContainer = Scheme.Light.onPrimaryContainer,
        secondary = Scheme.Light.secondary,
        onSecondary = Scheme.Light.onSecondary,
        secondaryContainer = Scheme.Light.secondaryContainer,
        onSecondaryContainer = Scheme.Light.onSecondaryContainer,
        tertiary = Scheme.Light.tertiary,
        onTertiary = Scheme.Light.onTertiary,
        tertiaryContainer = Scheme.Light.tertiaryContainer,
        onTertiaryContainer = Scheme.Light.onTertiaryContainer,
        error = Scheme.Light.error,
        onError = Scheme.Light.onError,
        errorContainer = Scheme.Light.errorContainer,
        onErrorContainer = Scheme.Light.onErrorContainer,
        background = Scheme.Light.background,
        onBackground = Scheme.Light.onBackground,
        surface = Scheme.Light.surface,
        surfaceVariant = Scheme.Light.surfaceVariant,
        onSurfaceVariant = Scheme.Light.onSurfaceVariant,
        outline = Scheme.Light.outline,
        outlineVariant = Scheme.Light.outlineVariant,
        scrim = Scheme.Light.scrim,
        inverseSurface = Scheme.Light.inverseSurface,
        inverseOnSurface = Scheme.Light.inverseOnSurface,
        inversePrimary = Scheme.Light.inversePrimary,
        surfaceDim = Scheme.Light.surfaceDim,
        surfaceBright = Scheme.Light.surfaceBright,
        surfaceContainerLowest = Scheme.Light.surfaceContainerLowest,
        surfaceContainerLow = Scheme.Light.surfaceContainerLow,
        surfaceContainer = Scheme.Light.surfaceContainer,
        surfaceContainerHigh = Scheme.Light.surfaceContainerHigh,
        surfaceContainerHighest = Scheme.Light.surfaceContainerHighest
    )

    val darkScheme = darkColorScheme(
        primary = Scheme.Dark.primary,
        onPrimary = Scheme.Dark.onPrimary,
        primaryContainer = Scheme.Dark.primaryContainer,
        onPrimaryContainer = Scheme.Dark.onPrimaryContainer,
        secondary = Scheme.Dark.secondary,
        onSecondary = Scheme.Dark.onSecondary,
        secondaryContainer = Scheme.Dark.secondaryContainer,
        onSecondaryContainer = Scheme.Dark.onSecondaryContainer,
        tertiary = Scheme.Dark.tertiary,
        onTertiary = Scheme.Dark.onTertiary,
        tertiaryContainer = Scheme.Dark.tertiaryContainer,
        onTertiaryContainer = Scheme.Dark.onTertiaryContainer,
        error = Scheme.Dark.error,
        onError = Scheme.Dark.onError,
        errorContainer = Scheme.Dark.errorContainer,
        onErrorContainer = Scheme.Dark.onErrorContainer,
        background = Scheme.Dark.background,
        onBackground = Scheme.Dark.onBackground,
        surface = Scheme.Dark.surface,
        surfaceVariant = Scheme.Dark.surfaceVariant,
        onSurfaceVariant = Scheme.Dark.onSurfaceVariant,
        outline = Scheme.Dark.outline,
        outlineVariant = Scheme.Dark.outlineVariant,
        scrim = Scheme.Dark.scrim,
        inverseSurface = Scheme.Dark.inverseSurface,
        inverseOnSurface = Scheme.Dark.inverseOnSurface,
        inversePrimary = Scheme.Dark.inversePrimary,
        surfaceDim = Scheme.Dark.surfaceDim,
        surfaceBright = Scheme.Dark.surfaceBright,
        surfaceContainerLowest = Scheme.Dark.surfaceContainerLowest,
        surfaceContainerLow = Scheme.Dark.surfaceContainerLow,
        surfaceContainer = Scheme.Dark.surfaceContainer,
        surfaceContainerHigh = Scheme.Dark.surfaceContainerHigh,
        surfaceContainerHighest = Scheme.Dark.surfaceContainerHighest
    )

    @Serializable
    sealed interface Scheme {
        val primary: Color
        val onPrimary: Color
        val primaryContainer: Color
        val onPrimaryContainer: Color

        val secondary: Color
        val onSecondary: Color
        val secondaryContainer: Color
        val onSecondaryContainer: Color

        val tertiary: Color
        val onTertiary: Color
        val tertiaryContainer: Color
        val onTertiaryContainer: Color

        val error: Color
        val onError: Color
        val errorContainer: Color
        val onErrorContainer: Color

        val background: Color
        val onBackground: Color

        val surface: Color
        val onSurface: Color
        val surfaceVariant: Color
        val onSurfaceVariant: Color

        val outline: Color
        val outlineVariant: Color
        val scrim: Color

        val inverseSurface: Color
        val inverseOnSurface: Color
        val inversePrimary: Color

        val surfaceDim: Color
        val surfaceBright: Color
        val surfaceContainerLowest: Color
        val surfaceContainerLow: Color
        val surfaceContainer: Color
        val surfaceContainerHigh: Color
        val surfaceContainerHighest: Color

        @Serializable
        data object Light : Scheme {
            override val primary: Color = Color(0xFF8C4A5F)
            override val onPrimary: Color = Color(0xFFFFFFFF)
            override val primaryContainer: Color = Color(0xFFFFD9E2)
            override val onPrimaryContainer: Color = Color(0xFF703348)

            override val secondary: Color = Color(0xFF74565E)
            override val onSecondary: Color = Color(0xFFFFFFFF)
            override val secondaryContainer: Color = Color(0xFFFFD9E2)
            override val onSecondaryContainer: Color = Color(0xFF5B3F47)

            override val tertiary: Color = Color(0xFF7C5634)
            override val onTertiary: Color = Color(0xFFFFFFFF)
            override val tertiaryContainer: Color = Color(0xFFFFDCC1)
            override val onTertiaryContainer: Color = Color(0xFF613F1F)

            override val error: Color = Color(0xFFBA1A1A)
            override val onError: Color = Color(0xFFFFFFFF)
            override val errorContainer: Color = Color(0xFFFFDAD6)
            override val onErrorContainer: Color = Color(0xFF93000A)

            override val background: Color = Color(0xFFFFF8F8)
            override val onBackground: Color = Color(0xFF22191B)

            override val surface: Color = Color(0xFFFFF8F8)
            override val onSurface: Color = Color(0xFF22191B)
            override val surfaceVariant: Color = Color(0xFFF2DDE1)
            override val onSurfaceVariant: Color = Color(0xFF514346)

            override val outline: Color = Color(0xFF837376)
            override val outlineVariant: Color = Color(0xFFD5C2C5)
            override val scrim: Color = Color(0xFF000000)

            override val inverseSurface: Color = Color(0xFF372E30)
            override val inverseOnSurface: Color = Color(0xFFFDEDEF)
            override val inversePrimary: Color = Color(0xFFFFB1C7)

            override val surfaceDim: Color = Color(0xFFE6D6D9)
            override val surfaceBright: Color = Color(0xFFFFF8F8)
            override val surfaceContainerLowest: Color = Color(0xFFFFFFFF)
            override val surfaceContainerLow: Color = Color(0xFFFFF0F2)
            override val surfaceContainer: Color = Color(0xFFFBEAEC)
            override val surfaceContainerHigh: Color = Color(0xFFF5E4E7)
            override val surfaceContainerHighest: Color = Color(0xFFEFDFE1)
        }

        @Serializable
        data object Dark : Scheme {
            override val primary: Color = Color(0xFFFFB1C7)
            override val onPrimary: Color = Color(0xFF541D31)
            override val primaryContainer: Color = Color(0xFF703348)
            override val onPrimaryContainer: Color = Color(0xFFFFD9E2)

            override val secondary: Color = Color(0xFFE3BDC6)
            override val onSecondary: Color = Color(0xFF422931)
            override val secondaryContainer: Color = Color(0xFF5B3F47)
            override val onSecondaryContainer: Color = Color(0xFFFFD9E2)

            override val tertiary: Color = Color(0xFFEEBD93)
            override val onTertiary: Color = Color(0xFF47290B)
            override val tertiaryContainer: Color = Color(0xFF613F1F)
            override val onTertiaryContainer: Color = Color(0xFFFFDCC1)

            override val error: Color = Color(0xFFFFB4AB)
            override val onError: Color = Color(0xFF690005)
            override val errorContainer: Color = Color(0xFF93000A)
            override val onErrorContainer: Color = Color(0xFFFFDAD6)

            override val background: Color = Color(0xFF191113)
            override val onBackground: Color = Color(0xFFEFDFE1)

            override val surface: Color = Color(0xFF191113)
            override val onSurface: Color = Color(0xFFEFDFE1)
            override val surfaceVariant: Color = Color(0xFF514346)
            override val onSurfaceVariant: Color = Color(0xFFD5C2C5)

            override val outline: Color = Color(0xFF9E8C90)
            override val outlineVariant: Color = Color(0xFF514346)
            override val scrim: Color = Color(0xFF000000)

            override val inverseSurface: Color = Color(0xFFEFDFE1)
            override val inverseOnSurface: Color = Color(0xFF372E30)
            override val inversePrimary: Color = Color(0xFF8C4A5F)

            override val surfaceDim: Color = Color(0xFF191113)
            override val surfaceBright: Color = Color(0xFF413739)
            override val surfaceContainerLowest: Color = Color(0xFF140C0E)
            override val surfaceContainerLow: Color = Color(0xFF22191B)
            override val surfaceContainer: Color = Color(0xFF261D1F)
            override val surfaceContainerHigh: Color = Color(0xFF31282A)
            override val surfaceContainerHighest: Color = Color(0xFF3C3234)
        }
    }
}