initscript {
    val spotlessVersion = "6.25.0"

    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("com.diffplug.spotless:spotless-plugin-gradle:$spotlessVersion")
    }
}

rootProject {
    allprojects {
        if (this == rootProject) return@allprojects
        apply<com.diffplug.gradle.spotless.SpotlessPlugin>()
        extensions.configure<com.diffplug.gradle.spotless.SpotlessExtension> {
            kotlin {
                target("**/*.kt")
                targetExclude("**/build/**/*.kt")
                ktlint().editorConfigOverride(
                    // https://pinterest.github.io/ktlint/latest/rules/standard
                    mapOf(
                        "android" to "true",
                        "insert_final_newline" to "false",
                        "ktlint_standard_function-naming" to "disabled",
                    ),
                )
                licenseHeaderFile(rootProject.file("spotless/copyright.kt"))
            }
        }
    }
}
