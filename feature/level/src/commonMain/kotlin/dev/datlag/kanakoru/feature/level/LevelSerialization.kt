package dev.datlag.kanakoru.feature.level

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import dev.datlag.kanakoru.feature.level.guidedtour.navigation.GuidedTour
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

object LevelSerialization {

    val featureModules = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(GuidedTour::class)
        }
    }

    val configuration = SavedStateConfiguration {
        serializersModule = featureModules
    }

}