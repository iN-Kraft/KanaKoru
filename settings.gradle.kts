rootProject.name = "KanaKoru"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        iNKraftRepository("Utils")
    }
}

include(":app", ":app:android", ":app:web", ":app:apple")
include(":core", ":core:dollarN", ":core:kodein", ":core:model", ":core:ui")

include(":feature")
include(":feature:home", ":feature:home:navigation")
include(":feature:kana", ":feature:kana:navigation")

fun findProperty(key: String): String? {
    val localProperties = java.util.Properties().apply {
        val file = rootDir.resolve("local.properties")
        if (file.exists()) {
            file.inputStream().use {
                load(it)
            }
        }
    }

    return providers.gradleProperty(key).orNull?.ifBlank {
        null
    } ?: localProperties.getProperty(key)?.ifBlank { null }
}

fun RepositoryHandler.iNKraftRepository(repository: String) {
    maven {
        name = "iNKraft $repository"
        url = uri("https://maven.pkg.github.com/iN-Kraft/$repository")
        credentials {
            username = findProperty("gpr.user") ?: System.getenv("PACKAGING_USERNAME")?.ifBlank { null }
            password = findProperty("gpr.password") ?: System.getenv("PACKAGING_PASSWORD")?.ifBlank { null }
        }
    }
}