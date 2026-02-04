import org.gradle.kotlin.dsl.android

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ktfmt)
    alias(libs.plugins.sentry)
}

android {
    namespace = "dev.datlag.kanakoru.android"
    compileSdk = 36

    defaultConfig {
        applicationId = "dev.datlag.kanakoru"
        minSdk = 23
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
    }
    buildFeatures {
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_21
    }
}

dependencies {
    implementation(libs.activity)
    implementation(libs.activity.compose)
    implementation(libs.android)
    implementation(libs.android.material)

    implementation(libs.coil)
    implementation(libs.inkraft.utils)
    implementation(libs.sentry)

    implementation(projects.app)

    coreLibraryDesugaring(libs.desugar)
}

ktfmt { kotlinLangStyle() }