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
    compilerOptions.freeCompilerArgs.add("-Xcontext-parameters")

    androidLibrary {
        compileSdk = 36
        minSdk = 21
        namespace = packageName

        androidResources { enable = true }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    js {
        browser()
    }
    wasmJs {
        browser()
    }

    applyDefaultHierarchyTemplate()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.resources)
            implementation(libs.compose.material3)
            implementation(libs.navigation3)
            implementation(libs.sentry)

            implementation(libs.coil.compose)
            implementation(libs.coil.network)
            implementation(libs.coil.svg)
            implementation(libs.immutable)
            implementation(libs.coroutines)

            implementation(projects.core.kodein)
            implementation(projects.core.model)
            implementation(projects.core.dollarN)
            implementation(libs.kodein.compose.runtime)
            implementation(libs.icons)
            implementation(libs.inkraft.cache)
            implementation(libs.inkraft.locale)
            implementation(libs.text.to.speech)
            implementation(libs.connectivity.compose)
        }

        commonTest.dependencies {
            implementation(libs.coroutines.test)
            implementation(libs.test)
        }

        androidMain.dependencies {
            implementation(libs.connectivity.compose.mobile)
        }

        appleMain.dependencies {
            implementation(libs.connectivity.compose.mobile)
        }

        webMain.dependencies {
            implementation(libs.connectivity)
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