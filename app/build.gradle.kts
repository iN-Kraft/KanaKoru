plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.detekt)
    alias(libs.plugins.serialization)
}

kotlin {
    androidLibrary {
        compileSdk = 36
        minSdk = 21
        namespace = "dev.datlag.kanakoru"

        androidResources { enable = true }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    applyDefaultHierarchyTemplate()

    sourceSets {
        commonMain.dependencies {
            api(libs.compose.material3)
            api(libs.navigation3.ui)
            // api(libs.navigation3.viewmodel)

            api(libs.kodein.compose)
            api(projects.core)
            api(projects.feature)
        }
    }
}
