import dev.detekt.gradle.Detekt

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.cocoapods) apply false
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.ktfmt) apply false
    alias(libs.plugins.multiplatform) apply false
    alias(libs.plugins.openrewrite) apply false
    alias(libs.plugins.sentry) apply false
    alias(libs.plugins.serialization) apply false
}

tasks.withType<Detekt>().configureEach {
    buildUponDefaultConfig.set(true)
    reports {
        checkstyle.required.set(true)
        markdown.required.set(true)
    }
}