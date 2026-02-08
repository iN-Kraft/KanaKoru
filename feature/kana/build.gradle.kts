plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.serialization)
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktfmt)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
}

val packageName = "dev.datlag.kanakoru.feature.kana"

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
            implementation(libs.compose.ui.tooling)
            implementation(libs.compose.material3)

            implementation(libs.coil.compose)
            api(projects.feature.kana.navigation)

            implementation(projects.core.kodein)
            implementation(projects.core.ui)
            implementation(projects.core.dollarN)
            implementation(projects.core.model)
            implementation(libs.kodein.compose.runtime)
            implementation(libs.inkraft.utils)
            implementation(libs.datetime)
            implementation(libs.icons)
            implementation(libs.lifecycle.viewmodel)
            implementation(libs.immutable)
        }

        commonTest.dependencies {
            implementation(libs.coroutines.test)
            implementation(libs.test)
        }
    }
}

compose.resources {
    publicResClass = false
    packageOfResClass = "$packageName.resources"
    generateResClass = auto
    nameOfResClass = "KanaRes"
}

ktfmt { kotlinLangStyle() }