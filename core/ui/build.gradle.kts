plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.serialization)
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktfmt)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
}

val packageName = "dev.datlag.kanakoru.ui"

kotlin {
    androidLibrary {
        compileSdk = 36
        minSdk = 21
        namespace = packageName

        androidResources { enable = true }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    applyDefaultHierarchyTemplate()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.resources)
            implementation(libs.compose.material3)
            implementation(libs.navigation3)
            implementation(libs.sentry)

            implementation(libs.coil.compose)
            implementation(libs.coil.svg)

            implementation(projects.core.kodein)
            implementation(libs.kodein.compose.runtime)
        }

        commonTest.dependencies {
            implementation(libs.coroutines.test)
            implementation(libs.test)
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "$packageName.resources"
    generateResClass = auto
    nameOfResClass = "CoreUIRes"
}

ktfmt { kotlinLangStyle() }