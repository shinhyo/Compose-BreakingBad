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

    ":core:common",
    ":core:data",
    ":core:database",
    ":core:datastore",
    ":core:network",
    ":core:model",
    ":core:domain",
    ":core:designsystem",

    ":feature:main",
    ":feature:bottombar",
    ":feature:list",
    ":feature:favorite",
    ":feature:setting",
    ":feature:detail"
)