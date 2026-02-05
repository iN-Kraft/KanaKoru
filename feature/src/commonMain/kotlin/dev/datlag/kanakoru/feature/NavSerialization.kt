package dev.datlag.kanakoru.feature

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import dev.datlag.kanakoru.feature.home.navigation.Home
import dev.datlag.kanakoru.feature.kana.navigation.Kana
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

object NavSerialization {

    val featureModules = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(Home::class)
            polymorphic(Kana::class) {
                subclass(Kana.Hiragana::class)
                subclass(Kana.Katakana::class)
            }
        }
    }

    val configuration = SavedStateConfiguration {
        serializersModule = featureModules
    }

}