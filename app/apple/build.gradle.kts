plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.cocoapods)
}

version = "0.0.0"

kotlin {
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        framework {
            baseName = "appleApp"
        }
    }
}