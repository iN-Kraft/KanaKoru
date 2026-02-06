plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ktfmt)
    alias(libs.plugins.sentry)
}

val packageName = "dev.datlag.kanakoru.web"

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
        wasmJsMain.dependencies {
            implementation(libs.ktor.wasm.js)
        }

        commonMain.dependencies {
            implementation(libs.compose.resources)
            implementation(libs.coil)
            implementation(libs.coil.network)
            implementation(libs.coil.svg)
            implementation(libs.ktor.js)
            implementation(projects.app)
            implementation(libs.immutable)
        }
    }
}

compose.resources {
    publicResClass = false
    packageOfResClass = "$packageName.resources"
    generateResClass = auto
    nameOfResClass = "WebRes"
}

ktfmt { kotlinLangStyle() }