package io.github.shinhyo.brba.buildlogic

import org.gradle.api.Project

internal fun Project.configureAndroidCompose() {
    androidExtension.apply {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = findVersion("androidxComposeCompiler").toString()
        }

    }
}
