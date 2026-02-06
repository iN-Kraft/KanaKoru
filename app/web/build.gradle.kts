plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ktfmt)
    alias(libs.plugins.sentry)
}

kotlin {
    js {
        browser()
        binaries.executable()
    }
    wasmJs {
        browser()
        binaries.executable()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.coil)
            implementation(projects.app)
        }
    }
}

ktfmt { kotlinLangStyle() }