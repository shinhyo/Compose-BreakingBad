val ktlintVersion = "0.43.0"

initscript {
    val spotlessVersion = "6.11.0"

    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("com.diffplug.spotless:spotless-plugin-gradle:$spotlessVersion")
    }
}

allprojects {
    if (this == rootProject) return@allprojects
    apply<com.diffplug.gradle.spotless.SpotlessPlugin>()
    extensions.configure<com.diffplug.gradle.spotless.SpotlessExtension> {
        kotlin {
            target("**/*.kt")
            targetExclude("**/build/**/*.kt")
            ktlint(ktlintVersion).userData(
                mapOf(
                    "android" to "true",
                    "disabled_rules" to "no-wildcard-imports,import-ordering"
                )
            )
            licenseHeaderFile(rootProject.file("spotless/copyright.kt"))
        }
    }
}