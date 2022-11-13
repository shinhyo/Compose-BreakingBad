pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "BrBa-Compose"
include(
    ":app",
//    ":presentation",
//    ":domain",
//    ":data",


    ":core:common",
    ":core:data",
    ":core:database",
    ":core:network",
    ":core:model",
    ":core:domain",
    ":core:designsystem",
    ":core:ui",

    ":feature:main",
    ":feature:detail"
//    ":feature:bottom:list",
//    ":feature:bottom:favorite",

)
