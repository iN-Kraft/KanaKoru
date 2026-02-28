plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktfmt)
    alias(libs.plugins.sqldelight)
}

val modulePackageName = "dev.datlag.kanakoru.repository.local"

kotlin {
    androidLibrary {
        compileSdk = 36
        minSdk = 21
        namespace = modulePackageName
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
            implementation(libs.serialization)
            implementation(libs.immutable)
            implementation(libs.sqldelight.coroutines)
            implementation(libs.codepoints)
            implementation(libs.datetime)
            implementation(projects.core.model)
        }

        androidMain.dependencies {
            implementation(libs.sqldelight.android)
        }

        webMain.dependencies {
            implementation(libs.sqldelight.browser)
            implementation(npm("sql.js", "1.14.0"))
            implementation(npm("@cashapp/sqldelight-sqljs-worker", libs.versions.sqldelight.get()))
            implementation(devNpm("copy-webpack-plugin", "13.0.1"))
        }

        nativeMain.dependencies {
            implementation(libs.sqldelight.native)
        }
    }
}

sqldelight {
    databases {
        create("KanaKoruDB") {
            packageName.set(modulePackageName)
            generateAsync.set(true)
        }
    }
}

ktfmt { kotlinLangStyle() }
