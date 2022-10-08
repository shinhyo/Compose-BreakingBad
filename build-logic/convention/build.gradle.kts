plugins {
    `kotlin-dsl`
}

group = "io.github.shinhyo.brba.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "brba.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "brba.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }

        register("androidHilt") {
            id = "brba.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }

        register("androidLibrary") {
            id = "brba.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "brba.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }

    }
}