pluginManagement {
    repositories {
        /*google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()*/
        maven { url = uri("https://maven.myket.ir/") }
        //maven { url = uri("https://maven.devneeds.ir") }
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        /*google()
        mavenCentral()
        maven(url = "https://jitpack.io")*/
        maven { url = uri("https://maven.myket.ir/") }
        //maven { url = uri("https://maven.devneeds.ir") }
    }
}

rootProject.name = "appointment"
include(
    ":app",
    ":core:ui",
    ":core:network",
    ":core:database",
    ":core:common",
    ":core:sync",
    ":feature:profile",
    ":feature:profile:domain",
    ":feature:specialty",
    ":feature:doctor",
    ":feature:doctor:domain",
    ":feature:appointment"
    )
