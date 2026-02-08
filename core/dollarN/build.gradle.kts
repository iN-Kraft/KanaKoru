plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktfmt)
}

kotlin {
    compilerOptions.freeCompilerArgs.add("-Xcontext-parameters")

    androidLibrary {
        compileSdk = 36
        minSdk = 21
        namespace = "dev.datlag.kanakoru.dollarN"
    }

    jvm()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    js {
        browser()
        nodejs()
    }
    wasmJs {
        browser()
        nodejs()
        d8()
    }

    applyDefaultHierarchyTemplate()

    sourceSets {
        commonMain.dependencies {
            api(libs.arrow)
            implementation(libs.inkraft.utils)
            implementation(libs.serialization)
            implementation(libs.immutable)
            implementation(projects.core.model)
        }
    }
}

ktfmt { kotlinLangStyle() }
