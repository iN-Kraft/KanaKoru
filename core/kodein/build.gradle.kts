plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktfmt)
}

kotlin {
    androidLibrary {
        compileSdk = 36
        minSdk = 21
        namespace = "dev.datlag.kanakoru.kodein"
    }

    jvm()
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
            api(libs.kodein)
            implementation(libs.reflection)
        }
    }
}

ktfmt { kotlinLangStyle() }
