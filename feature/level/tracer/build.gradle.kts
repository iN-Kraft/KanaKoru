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

val packageName = "dev.datlag.kanakoru.feature.level.tracer"

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

    js {
        browser()
    }
    wasmJs {
        nodejs()
    }

    applyDefaultHierarchyTemplate()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.bundles.feature)
            api(projects.feature.level.tracer.navigation)
            implementation(projects.core)
        }

        commonTest.dependencies {
            implementation(libs.bundles.test)
        }
    }
}

compose.resources {
    publicResClass = false
    packageOfResClass = "$packageName.resources"
    generateResClass = auto
    nameOfResClass = "TracerRes"
}

ktfmt { kotlinLangStyle() }