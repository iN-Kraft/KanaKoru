plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.serialization)
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktfmt)
}

kotlin {
    androidLibrary {
        compileSdk = 36
        minSdk = 21
        namespace = "dev.datlag.kanakoru.feature.kana.navigation"
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    applyDefaultHierarchyTemplate()

    sourceSets {
        commonMain.dependencies {
            api(libs.navigation3)
            implementation(libs.serialization)
        }

        commonTest.dependencies {
            implementation(libs.coroutines.test)
            implementation(libs.test)
        }
    }
}

ktfmt { kotlinLangStyle() }