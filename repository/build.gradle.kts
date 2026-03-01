plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktfmt)
}

val modulePackageName = "dev.datlag.kanakoru.repository"

kotlin {
    androidLibrary {
        compileSdk = 36
        minSdk = 21
        namespace = modulePackageName
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
            implementation(libs.arrow)
            implementation(libs.inkraft.utils)
            implementation(libs.serialization)
            implementation(libs.immutable)

            implementation(projects.repository.local)
            implementation(projects.core.model)
            implementation(projects.core.kodein)
        }
    }
}

ktfmt { kotlinLangStyle() }
